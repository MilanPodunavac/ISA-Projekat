package code.service.impl;

import code.model.User;
import code.repository.UserRepository;
import code.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository _userRepository;
    private final JavaMailSender _mailSender;

    public UserServiceImpl(UserRepository userRepository, JavaMailSender mailSender) {
        this._userRepository = userRepository;
        this._mailSender = mailSender;
    }

    @Override
    public User findById(Integer id) {
        return _userRepository.findById(id).orElseGet(null);
    }

    @Override
    public boolean isUserEnabled(Integer id) {
        User user = findById(id);
        return user.isEnabled();
    }

    @Override
    public boolean userExists(Integer id) {
        Optional<User> user = _userRepository.findById(id);
        return user.isPresent();
    }

    @Override
    public boolean userExists(String email) {
        if (_userRepository.findByEmail(email) != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> getUnverifiedProviders() {
        return _userRepository.findByEnabled(false);
    }

    @Override
    public void acceptRegistrationRequest(Integer id) {
        User user = findById(id);
        user.setEnabled(true);
        _userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Registration request");
        message.setText("Registration request accepted!");
        _mailSender.send(message);
    }

    @Override
    public void declineRegistrationRequest(Integer id, String declineReason) {
        User user = findById(id);
        _userRepository.delete(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Registration request");
        message.setText("Registration request declined: " + declineReason);
        _mailSender.send(message);
    }
}