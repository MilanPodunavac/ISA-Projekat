package code.service.impl;

import code.exceptions.admin.ModifyAnotherUserDataException;
import code.exceptions.admin.NotChangedPasswordException;
import code.exceptions.entities.AccountDeletionRequestDontExistException;
import code.exceptions.entities.EntityNotDeletableException;
import code.exceptions.entities.LoggedInUserAlreadySubmittedAccountDeletionRequestException;
import code.exceptions.fishing_trip.reservation.EnabledClientDoesntExistException;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.NotProviderException;
import code.exceptions.provider_registration.UserAccountActivatedException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.model.base.AvailabilityPeriod;
import code.model.cottage.CottageOwner;
import code.model.cottage.CottageReservation;
import code.repository.*;
import code.service.UserService;
import code.utils.FileUploadUtil;
import lombok.Builder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository _userRepository;
    private final ClientRepository _clientRepository;
    private final AvailabilityPeriodRepository _availabilityPeriodRepository;
    private final CottageRepository _cottageRepository;
    private final CottageReservationRepository _cottageReservationRepository;
    private final FishingTripPictureRepository _fishingTripPictureRepository;
    private final FishingTripRepository _fishingTripRepository;
    private final FishingTripQuickReservationRepository _fishingTripQuickReservationRepository;
    private final FishingTripReservationRepository _fishingTripReservationRepository;
    private final AccountDeletionRequestRepository _accountDeletionRequestRepository;
    private final JavaMailSender _mailSender;
    private final PasswordEncoder _passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ClientRepository clientRepository, AvailabilityPeriodRepository availabilityPeriodRepository, CottageRepository cottageRepository, CottageReservationRepository cottageReservationRepository, FishingTripPictureRepository fishingTripPictureRepository, FishingTripRepository fishingTripRepository, FishingTripQuickReservationRepository fishingTripQuickReservationRepository, FishingTripReservationRepository fishingTripReservationRepository, AccountDeletionRequestRepository accountDeletionRequestRepository, JavaMailSender mailSender, PasswordEncoder encoder) {
        this._userRepository = userRepository;
        this._clientRepository = clientRepository;
        this._availabilityPeriodRepository = availabilityPeriodRepository;
        this._cottageRepository = cottageRepository;
        this._cottageReservationRepository = cottageReservationRepository;
        this._fishingTripPictureRepository = fishingTripPictureRepository;
        this._fishingTripRepository = fishingTripRepository;
        this._fishingTripQuickReservationRepository = fishingTripQuickReservationRepository;
        this._fishingTripReservationRepository = fishingTripReservationRepository;
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

    private boolean userClient(Integer id) {
        User user = _userRepository.getById(id);
        return user.getRole().getName().equals("ROLE_CLIENT");
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
    public void submitAccountDeletionRequest(AccountDeletionRequest accountDeletionRequest) throws LoggedInUserAlreadySubmittedAccountDeletionRequestException {
        User loggedInUser = getLoggedInUser();
        throwExceptionIfLoggedInUserAlreadySubmittedAccountDeletionRequest(loggedInUser);
        accountDeletionRequest.setUser(loggedInUser);
        _accountDeletionRequestRepository.save(accountDeletionRequest);
    }

    private void throwExceptionIfLoggedInUserAlreadySubmittedAccountDeletionRequest(User loggedInUser) throws LoggedInUserAlreadySubmittedAccountDeletionRequestException {
        AccountDeletionRequest accountDeletionRequest = _accountDeletionRequestRepository.findByUserId(loggedInUser.getId());
        if (accountDeletionRequest != null) {
            throw new LoggedInUserAlreadySubmittedAccountDeletionRequestException("You already submitted account deletion request!");
        }
    }

    private void checkIfUserDeletable(User loggedInUser) throws EntityNotDeletableException {
        if (loggedInUser.getRole().getName().equals("ROLE_FISHING_INSTRUCTOR")) {
            checkIfFishingInstructorDeletable((FishingInstructor) loggedInUser);
        } else if (loggedInUser.getRole().getName().equals("ROLE_COTTAGE_OWNER")) {
            checkIfCottageOwnerDeletable((CottageOwner) loggedInUser);
        } else if (loggedInUser.getRole().getName().equals("ROLE_BOAT_OWNER")) {
            checkIfBoatOwnerDeletable((BoatOwner) loggedInUser);
        } else if (loggedInUser.getRole().getName().equals("ROLE_CLIENT")) {
            checkIfClientDeletable((Client) loggedInUser);
        }
    }

    @Override
    public void checkIfFishingInstructorDeletable(FishingInstructor fishingInstructor) throws EntityNotDeletableException {
        List<Integer> instructorFishingTripIds = _fishingTripRepository.findByFishingInstructor(fishingInstructor.getId());
        List<FishingTripQuickReservation> instructorQuickReservations =  _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        for (FishingTripQuickReservation fishingTripQuickReservation : instructorQuickReservations) {
            if (fishingTripQuickReservation.getClient() != null && fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays()).isAfter(LocalDate.now())) {
                throw new EntityNotDeletableException("You can't delete an entity that has reservations!");
            }
        }

        List<FishingTripReservation> instructorReservations =  _fishingTripReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        for (FishingTripReservation fishingTripReservation : instructorReservations) {
            if (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays()).isAfter(LocalDate.now())) {
                throw new EntityNotDeletableException("You can't delete an entity that has reservations!");
            }
        }
    }

    @Override
    public void checkIfCottageOwnerDeletable(CottageOwner cottageOwner) throws EntityNotDeletableException {
        List<Integer> cottageOwnerCottageIds = _cottageRepository.findByCottageOwner(cottageOwner.getId());
        List<CottageReservation> cottageOwnerReservations =  _cottageReservationRepository.findByCottageIdIn(cottageOwnerCottageIds);
        for (CottageReservation cottageReservation : cottageOwnerReservations) {
            if (cottageReservation.getClient() != null && cottageReservation.getDateRange().getEndDate().after(new Date())) {
                throw new EntityNotDeletableException("You can't delete an entity that has reservations!");
            }
        }
    }

    @Override
    public void checkIfBoatOwnerDeletable(BoatOwner boatOwner) throws EntityNotDeletableException {

    }

    @Override
    public void checkIfClientDeletable(Client client) throws EntityNotDeletableException {
        List<FishingTripQuickReservation> clientFishingTripQuickReservations = _fishingTripQuickReservationRepository.findByClientId(client.getId());
        List<FishingTripReservation> clientFishingTripReservations = _fishingTripReservationRepository.findByClientId(client.getId());
        List<CottageReservation> clientCottageReservations = _cottageReservationRepository.findByClientId(client.getId());

        for (FishingTripQuickReservation fishingTripQuickReservation : clientFishingTripQuickReservations) {
            if (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays()).isAfter(LocalDate.now())) {
                throw new EntityNotDeletableException("You can't delete an entity that has reservations!");
            }
        }

        for (FishingTripReservation fishingTripQuickReservation : clientFishingTripReservations) {
            if (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays()).isAfter(LocalDate.now())) {
                throw new EntityNotDeletableException("You can't delete an entity that has reservations!");
            }
        }

        for (CottageReservation cottageReservation : clientCottageReservations) {
            if (cottageReservation.getDateRange().getEndDate().after(new Date())) {
                throw new EntityNotDeletableException("You can't delete an entity that has reservations!");
            }
        }
    }

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    @Transactional
    @Override
    public void declineAccountDeletionRequest(Integer id, String responseText) throws AccountDeletionRequestDontExistException, NotChangedPasswordException {
        throwExceptionIfAccountDeletionRequestDontExist(id);
        throwExceptionIfAdminDidntChangePassword();
        AccountDeletionRequest accountDeletionRequest = _accountDeletionRequestRepository.getById(id);
        _accountDeletionRequestRepository.delete(accountDeletionRequest);
        sendDeclineAccountDeletionRequestEmail(accountDeletionRequest.getUser().getEmail(), responseText);
    }

    private void throwExceptionIfAdminDidntChangePassword() throws NotChangedPasswordException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Admin admin = (Admin) user;
        if (!admin.isPasswordChanged()) {
            throw new NotChangedPasswordException("Password not changed!");
        }
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
    public void acceptAccountDeletionRequest(Integer id, String responseText) throws AccountDeletionRequestDontExistException, NotChangedPasswordException, EntityNotDeletableException {
        throwExceptionIfAccountDeletionRequestDontExist(id);
        throwExceptionIfAdminDidntChangePassword();
        AccountDeletionRequest accountDeletionRequest = _accountDeletionRequestRepository.getById(id);
        checkIfUserDeletable(accountDeletionRequest.getUser());
        _accountDeletionRequestRepository.delete(accountDeletionRequest);
        deleteUserLogic(accountDeletionRequest);
        sendAcceptAccountDeletionRequestEmail(accountDeletionRequest.getUser().getEmail(), responseText);
    }

    private void deleteUserLogic(AccountDeletionRequest accountDeletionRequest) {
        if (accountDeletionRequest.getUser().getRole().getName().equals("ROLE_FISHING_INSTRUCTOR")) {
            unlinkReferencesFishingInstructor((FishingInstructor) accountDeletionRequest.getUser());
        } else if (accountDeletionRequest.getUser().getRole().getName().equals("ROLE_COTTAGE_OWNER")) {
            unlinkReferencesCottageOwner((CottageOwner) accountDeletionRequest.getUser());
        } else if (accountDeletionRequest.getUser().getRole().getName().equals("ROLE_BOAT_OWNER")) {
            unlinkReferencesBoatOwner((BoatOwner) accountDeletionRequest.getUser());
        } else if (accountDeletionRequest.getUser().getRole().getName().equals("ROLE_CLIENT")) {
            unlinkReferencesClient((Client) accountDeletionRequest.getUser());
        }

        _userRepository.delete(accountDeletionRequest.getUser());
    }

    @Override
    public void unlinkReferencesFishingInstructor(FishingInstructor fishingInstructor) {
        deleteFishingTripReservations(fishingInstructor.getId());
        unlinkFishingTripQuickReservations(fishingInstructor.getId());
        deleteSubscribers(fishingInstructor);
        deleteFishingTripPictures(fishingInstructor);
    }

    private void deleteFishingTripReservations(Integer id) {
        List<Integer> instructorFishingTripIds = _fishingTripRepository.findByFishingInstructor(id);
        List<FishingTripReservation> fishingTripReservations = _fishingTripReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        for (FishingTripReservation fishingTripReservation : fishingTripReservations) {
            fishingTripReservation.setFishingTrip(null);

            fishingTripReservation.getClient().getFishingTripReservations().remove(fishingTripReservation);
            fishingTripReservation.setClient(null);

            _fishingTripReservationRepository.delete(fishingTripReservation);
        }
    }

    private void unlinkFishingTripQuickReservations(Integer id) {
        List<Integer> instructorFishingTripIds = _fishingTripRepository.findByFishingInstructor(id);
        List<FishingTripQuickReservation> fishingTripQuickReservations = _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        for (FishingTripQuickReservation fishingTripQuickReservation : fishingTripQuickReservations) {
            if (fishingTripQuickReservation.getClient() != null) {
                fishingTripQuickReservation.getClient().getFishingTripQuickReservations().remove(fishingTripQuickReservation);
                fishingTripQuickReservation.setClient(null);
            }
        }
    }

    private void deleteSubscribers(FishingInstructor fishingInstructor) {
        List<Client> subscribers = new ArrayList<>();
        subscribers.addAll(fishingInstructor.getSubscribers());
        ListIterator<Client> subscribersIterator = subscribers.listIterator();
        while (subscribersIterator.hasNext()) {
            subscribersIterator.next().getInstructorsSubscribedTo().remove(fishingInstructor);
            subscribersIterator.remove();
        }
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

    @Override
    public void unlinkReferencesCottageOwner(CottageOwner cottageOwner) {
        List<Integer> cottageOwnerCottageIds = _cottageRepository.findByCottageOwner(cottageOwner.getId());
        List<CottageReservation> cottageOwnerReservations =  _cottageReservationRepository.findByCottageIdIn(cottageOwnerCottageIds);
        List<AvailabilityPeriod> allAvailabilityPeriods = _availabilityPeriodRepository.findAll();
        List<Client> allClients = _clientRepository.findAll();

        for (CottageReservation cottageReservation : cottageOwnerReservations) {
            cottageReservation.setCottage(null);
        }

        for (AvailabilityPeriod availabilityPeriod : allAvailabilityPeriods) {
            for (CottageReservation cottageReservation : cottageOwnerReservations) {
                if (availabilityPeriod.getReservations().contains(cottageReservation)) {
                    availabilityPeriod.getReservations().remove(cottageReservation);
                    cottageReservation.setAvailabilityPeriod(null);
                }
            }
        }

        for (Client client : allClients) {
            for (CottageReservation cottageReservation : cottageOwnerReservations) {
                if (client.getReservation().contains(cottageReservation)) {
                    client.getReservation().remove(cottageReservation);
                    cottageReservation.setClient(null);
                }
            }
        }

        for (CottageReservation cottageReservation : cottageOwnerReservations) {
            _cottageReservationRepository.delete(cottageReservation);
        }
    }

    @Override
    public void unlinkReferencesBoatOwner(BoatOwner boatOwner) {

    }

    @Override
    public void unlinkReferencesClient(Client client) {
        List<FishingTripQuickReservation> clientFishingTripQuickReservations = _fishingTripQuickReservationRepository.findByClientId(client.getId());
        List<CottageReservation> clientCottageReservations = _cottageReservationRepository.findByClientId(client.getId());

        for (FishingTripQuickReservation fishingTripQuickReservation : clientFishingTripQuickReservations) {
            client.getFishingTripQuickReservations().remove(fishingTripQuickReservation);
            fishingTripQuickReservation.setClient(null);

            fishingTripQuickReservation.getFishingTrip().getFishingTripQuickReservations().remove(fishingTripQuickReservation);
            fishingTripQuickReservation.setFishingTrip(null);

            _fishingTripQuickReservationRepository.delete(fishingTripQuickReservation);
        }

        for (CottageReservation cottageReservation : clientCottageReservations) {
            client.getReservation().remove(cottageReservation);
            cottageReservation.setClient(null);

            cottageReservation.setCottage(null);

            cottageReservation.getAvailabilityPeriod().getReservations().remove(cottageReservation);
            cottageReservation.setAvailabilityPeriod(null);

            _cottageReservationRepository.delete(cottageReservation);
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

    @Override
    public void throwExceptionIfEnabledClientDoesntExist(Integer clientId) throws EnabledClientDoesntExistException {
        if (!userExists(clientId) || !isUserEnabled(clientId) ||  !userClient(clientId)) {
            throw new EnabledClientDoesntExistException("User isn't enabled client or doesn't exist!");
        }
    }
}