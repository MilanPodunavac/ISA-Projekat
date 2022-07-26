package code.service.impl;

import code.model.BoatOwner;
import code.model.CottageOwner;
import code.model.FishingInstructor;
import code.model.User;
import code.repository.BoatOwnerRepository;
import code.repository.CottageOwnerRepository;
import code.repository.FishingInstructorRepository;
import code.repository.UserRepository;
import code.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository _userRepository;
    private final CottageOwnerRepository _cottageOwnerRepository;
    private final BoatOwnerRepository _boatOwnerRepository;
    private final FishingInstructorRepository _fishingInstructorRepository;
    private final JavaMailSender _mailSender;

    public UserServiceImpl(UserRepository userRepository, CottageOwnerRepository cottageOwnerRepository, BoatOwnerRepository boatOwnerRepository, FishingInstructorRepository fishingInstructorRepository, JavaMailSender mailSender) {
        this._userRepository = userRepository;
        this._cottageOwnerRepository = cottageOwnerRepository;
        this._boatOwnerRepository = boatOwnerRepository;
        this._fishingInstructorRepository = fishingInstructorRepository;
        this._mailSender = mailSender;
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
        List<User> cottageOwnersAndBoatOwners = Stream.concat(_cottageOwnerRepository.findByEnabled(false).stream(), _boatOwnerRepository.findByEnabled(false).stream())
                .collect(Collectors.toList());
        List<User> unverifiedProviders = Stream.concat(cottageOwnersAndBoatOwners.stream(), _fishingInstructorRepository.findByEnabled(false).stream())
                .collect(Collectors.toList());
        return unverifiedProviders;
    }

    @Override
    public void acceptRegistrationRequest(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(email);
        message.setSubject("Registration request");
        message.setText("Registration request accepted!");
        _mailSender.send(message);

        if (_cottageOwnerRepository.findByEmail(email) != null) {
            CottageOwner cottageOwner = _cottageOwnerRepository.findByEmail(email);
            cottageOwner.setEnabled(true);
            _cottageOwnerRepository.save(cottageOwner);
        } else if (_boatOwnerRepository.findByEmail(email) != null) {
            BoatOwner boatOwner = _boatOwnerRepository.findByEmail(email);
            boatOwner.setEnabled(true);
            _boatOwnerRepository.save(boatOwner);
        } else {
            FishingInstructor fishingInstructor = _fishingInstructorRepository.findByEmail(email);
            fishingInstructor.setEnabled(true);
            _fishingInstructorRepository.save(fishingInstructor);
        }
    }

    @Override
    public void declineRegistrationRequest(String email, String declineReason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(email);
        message.setSubject("Registration request");
        message.setText("Registration request declined: " + declineReason);
        _mailSender.send(message);

        if (_cottageOwnerRepository.findByEmail(email) != null) {
            CottageOwner cottageOwner = _cottageOwnerRepository.findByEmail(email);
            _cottageOwnerRepository.delete(cottageOwner);
        } else if (_boatOwnerRepository.findByEmail(email) != null) {
            BoatOwner boatOwner = _boatOwnerRepository.findByEmail(email);
            _boatOwnerRepository.delete(boatOwner);
        } else {
            FishingInstructor fishingInstructor = _fishingInstructorRepository.findByEmail(email);
            _fishingInstructorRepository.delete(fishingInstructor);
        }
    }
}