package code.service.impl;

import code.exceptions.registration.EmailTakenException;
import code.exceptions.registration.UserAccountActivatedException;
import code.exceptions.registration.UserNotFoundException;
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

    private boolean isUserEnabled(Integer id) {
        User user = findById(id);
        return user.isEnabled();
    }

    private boolean userExists(Integer id) {
        Optional<User> user = _userRepository.findById(id);
        return user.isPresent();
    }

    @Override
    public void throwExceptionIfEmailExists(String email) throws EmailTakenException {
        if (_userRepository.findByEmail(email) != null) {
            throw new EmailTakenException("User with entered email already exists!");
        }
    }

    @Override
    public List<User> getUnverifiedProviders() {
        return _userRepository.findByEnabled(false);
    }

    @Override
    public void acceptRegistrationRequest(Integer id) throws UserNotFoundException, UserAccountActivatedException {
        throwExceptionIfUserEnabledOrUserDontExist(id);
        User user = enableUserAccount(id);
        sendAcceptRegistrationRequestEmail(user.getEmail());
    }

    private User enableUserAccount(Integer id) {
        User user = findById(id);
        user.setEnabled(true);
        _userRepository.save(user);

        return user;
    }

    private void sendAcceptRegistrationRequestEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(email);
        message.setSubject("Registration request");
        message.setText("Registration request accepted!");
        _mailSender.send(message);
    }

    @Override
    public void declineRegistrationRequest(Integer id, String declineReason) throws UserNotFoundException, UserAccountActivatedException {
        throwExceptionIfUserEnabledOrUserDontExist(id);
        User user = deleteRegistrationRequest(id);
        sendDeclineRegistrationRequestEmail(user.getEmail(), declineReason);
    }

    private void throwExceptionIfUserEnabledOrUserDontExist(Integer id) throws UserNotFoundException, UserAccountActivatedException {
        if(!userExists(id)) {
            throw new UserNotFoundException("User not found!");
        }

        if(isUserEnabled(id)) {
            throw new UserAccountActivatedException("User account is already activated!");
        }
    }

    private User deleteRegistrationRequest(Integer id) {
        User user = findById(id);
        _userRepository.delete(user);

        return user;
    }

    private void sendDeclineRegistrationRequestEmail(String email, String declineReason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(email);
        message.setSubject("Registration request");
        message.setText("Registration request declined: " + declineReason);
        _mailSender.send(message);
    }
}