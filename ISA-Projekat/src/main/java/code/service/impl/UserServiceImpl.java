package code.service.impl;

import code.exceptions.admin.ModifyAnotherUserDataException;
import code.exceptions.entities.AccountDeletionRequestDontExistException;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.NotProviderException;
import code.exceptions.provider_registration.UserAccountActivatedException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.repository.*;
import code.service.FishingInstructorService;
import code.service.UserService;
import code.utils.FileUploadUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository _userRepository;
    private final FishingInstructorAvailablePeriodRepository _fishingInstructorAvailablePeriodRepository;
    private final FishingTripRepository _fishingTripRepository;
    private final FishingTripPictureRepository _fishingTripPictureRepository;
    private final AccountDeletionRequestRepository _accountDeletionRequestRepository;
    private final JavaMailSender _mailSender;
    private final PasswordEncoder _passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, FishingInstructorAvailablePeriodRepository fishingInstructorAvailablePeriodRepository, FishingTripRepository fishingTripRepository, FishingTripPictureRepository fishingTripPictureRepository, AccountDeletionRequestRepository accountDeletionRequestRepository, JavaMailSender mailSender, PasswordEncoder encoder) {
        this._userRepository = userRepository;
        this._fishingInstructorAvailablePeriodRepository = fishingInstructorAvailablePeriodRepository;
        this._fishingTripRepository = fishingTripRepository;
        this._fishingTripPictureRepository = fishingTripPictureRepository;
        this._accountDeletionRequestRepository = accountDeletionRequestRepository;
        this._mailSender = mailSender;
        _passwordEncoder = encoder;
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
    public void throwExceptionIfModifyAnotherUserData(Integer id) throws ModifyAnotherUserDataException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (user.getId() != id) {
            throw new ModifyAnotherUserDataException("You can't modify another user data!");
        }
    }

    @Override
    public void throwExceptionIfUserDontExist(Integer id) throws UserNotFoundException {
        if (!userExists(id)) {
            throw new UserNotFoundException("User not found!");
        }
    }

    @Override
    public List<User> getUnverifiedProviders() {
        List<User> users =  _userRepository.findByEnabled(false);
        List<User> unverifiedProviders = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().getName().equals("ROLE_COTTAGE_OWNER") || user.getRole().getName().equals("ROLE_BOAT_OWNER") || user.getRole().getName().equals("ROLE_FISHING_INSTRUCTOR")) {
                unverifiedProviders.add(user);
            }
        }

        return unverifiedProviders;
    }

    @Override
    public void acceptRegistrationRequest(Integer id) throws UserNotFoundException, UserAccountActivatedException, NotProviderException {
        throwExceptionIfUserEnabledOrUserDontExist(id);
        throwExceptionIfNotProvider(id);
        User user = enableUserAccount(id);
        sendAcceptRegistrationRequestEmail(user.getEmail());
    }

    private User enableUserAccount(Integer id) {
        User user = findById(id);
        user.setEnabled(true);
        return _userRepository.save(user);
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
    public void declineRegistrationRequest(Integer id, String declineReason) throws UserNotFoundException, UserAccountActivatedException, NotProviderException {
        throwExceptionIfUserEnabledOrUserDontExist(id);
        throwExceptionIfNotProvider(id);
        User user = deleteRegistrationRequest(id);
        sendDeclineRegistrationRequestEmail(user.getEmail(), declineReason);
    }

    private void throwExceptionIfUserEnabledOrUserDontExist(Integer id) throws UserNotFoundException, UserAccountActivatedException {
        if (!userExists(id)) {
            throw new UserNotFoundException("User not found!");
        }

        if (isUserEnabled(id)) {
            throw new UserAccountActivatedException("User account is already activated!");
        }
    }

    private void throwExceptionIfNotProvider(Integer id) throws NotProviderException {
        User user = findById(id);
        if (!user.getRole().getName().equals("ROLE_COTTAGE_OWNER") && !user.getRole().getName().equals("ROLE_BOAT_OWNER") && !user.getRole().getName().equals("ROLE_FISHING_INSTRUCTOR")) {
            throw new NotProviderException("User isn't a provider!");
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

    @Override
    public void updatePersonalInformation(User user) throws UserNotFoundException {
        User userFromRepo = _userRepository.findByEmail(user.getEmail());
        if(userFromRepo == null) throw new UserNotFoundException("User with email " + user.getEmail() + "does not exist!");
        userFromRepo.setLocation(user.getLocation());
        userFromRepo.setFirstName(user.getFirstName());
        userFromRepo.setLastName(user.getLastName());
        userFromRepo.setPhoneNumber(user.getPhoneNumber());
        _userRepository.save(userFromRepo);
    }

    @Override
    public void changePassword(String newPassword, String email) throws UserNotFoundException {
        User userFromRepo = _userRepository.findByEmail(email);
        if(userFromRepo == null) throw new UserNotFoundException("User with email " + email + "does not exist!");
        userFromRepo.setPassword(_passwordEncoder.encode(newPassword));
        _userRepository.save(userFromRepo);
    }

    @Override
    public void submitAccountDeletionRequest(AccountDeletionRequest accountDeletionRequest) {
        accountDeletionRequest.setUser(getLoggedInUser());
        _accountDeletionRequestRepository.save(accountDeletionRequest);
    }

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    @Transactional
    @Override
    public void declineAccountDeletionRequest(Integer id, String responseText) throws AccountDeletionRequestDontExistException {
        throwExceptionIfAccountDeletionRequestDontExist(id);
        AccountDeletionRequest accountDeletionRequest = _accountDeletionRequestRepository.getById(id);
        _accountDeletionRequestRepository.delete(accountDeletionRequest);
        sendDeclineAccountDeletionRequestEmail(accountDeletionRequest.getUser().getEmail(), responseText);
    }

    private void throwExceptionIfAccountDeletionRequestDontExist(Integer id) throws AccountDeletionRequestDontExistException {
        Optional<AccountDeletionRequest> accountDeletionRequest = _accountDeletionRequestRepository.findById(id);
        if(!accountDeletionRequest.isPresent()) {
            throw new AccountDeletionRequestDontExistException("Account deletion request don't exist!");
        }
    }

    private void sendDeclineAccountDeletionRequestEmail(String email, String responseText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(email);
        message.setSubject("Account deletion request");
        message.setText("Account deletion request declined: " + responseText);
        _mailSender.send(message);
    }

    @Transactional
    @Override
    public void acceptAccountDeletionRequest(Integer id, String responseText) throws AccountDeletionRequestDontExistException {
        throwExceptionIfAccountDeletionRequestDontExist(id);
        AccountDeletionRequest accountDeletionRequest = _accountDeletionRequestRepository.getById(id);
        _accountDeletionRequestRepository.delete(accountDeletionRequest);
        deleteUserLogic(accountDeletionRequest);
        sendAcceptAccountDeletionRequestEmail(accountDeletionRequest.getUser().getEmail(), responseText);
    }

    private void deleteUserLogic(AccountDeletionRequest accountDeletionRequest) {
        if (accountDeletionRequest.getUser().getRole().getName().equals("ROLE_FISHING_INSTRUCTOR")) {
            deleteFishingTripPictures((FishingInstructor) accountDeletionRequest.getUser());
        } else if (accountDeletionRequest.getUser().getRole().getName().equals("ROLE_COTTAGE_OWNER")) {
            //unlinkReferencesCottageOwner((CottageOwner) accountDeletionRequest.getUser());
        } else if (accountDeletionRequest.getUser().getRole().getName().equals("ROLE_BOAT_OWNER")) {
            //unlinkReferencesBoatOwner((BoatOwner) accountDeletionRequest.getUser());
        } else if (accountDeletionRequest.getUser().getRole().getName().equals("ROLE_CLIENT")) {
            //unlinkReferencesClient((Client) accountDeletionRequest.getUser());
        }

        _userRepository.delete(accountDeletionRequest.getUser());
    }

    private void deleteFishingTripPictures(FishingInstructor fishingInstructor) {
        String fishingTripPicturesDirectory = "fishing_trip_pictures";
        for (FishingTrip fishingTrip : fishingInstructor.getFishingTrips()) {
            List<FishingTripPicture> fishingTripPictures = _fishingTripPictureRepository.findByFishingTrip(fishingTrip.getId());
            for (FishingTripPicture fishingTripPicture : fishingTripPictures) {
                FileUploadUtil.deleteFile(fishingTripPicturesDirectory, fishingTripPicture.getId() + "_" + fishingTripPicture.getName());
            }
        }
    }

    private void sendAcceptAccountDeletionRequestEmail(String email, String responseText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(email);
        message.setSubject("Account deletion request");
        message.setText("Account deletion request accepted: " + responseText);
        _mailSender.send(message);
    }
}