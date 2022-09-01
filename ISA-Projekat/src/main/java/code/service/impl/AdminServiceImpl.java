package code.service.impl;

import code.dto.admin.ComplaintResponse;
import code.exceptions.admin.*;
import code.exceptions.entities.*;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.model.base.Action;
import code.model.base.Reservation;
import code.model.boat.Boat;
import code.model.boat.BoatAction;
import code.model.boat.BoatOwner;
import code.model.boat.BoatReservation;
import code.model.cottage.Cottage;
import code.model.cottage.CottageAction;
import code.model.cottage.CottageOwner;
import code.model.cottage.CottageReservation;
import code.repository.*;
import code.service.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository _adminRepository;
    private final PasswordEncoder _passwordEncoder;
    private final RoleService _roleService;
    private final UserService _userService;
    private final UserRepository _userRepository;
    private final CottageService _cottageService;
    private final BoatService _boatService;
    private final ClientRepository _clientRepository;
    private final FishingInstructorRepository _fishingInstructorRepository;
    private final FishingTripReservationRepository _fishingTripReservationRepository;
    private final FishingTripQuickReservationRepository _fishingTripQuickReservationRepository;
    private final ReservationRepository _reservationRepository;
    private final ActionRepository _actionRepository;
    private final CurrentSystemTaxPercentageRepository _currentSystemTaxPercentageRepository;
    private final CurrentPointsClientGetsAfterReservationRepository _currentPointsClientGetsAfterReservationRepository;
    private final CurrentPointsProviderGetsAfterReservationRepository _currentPointsProviderGetsAfterReservationRepository;
    private final LoyaltyProgramClientRepository _loyaltyProgramClientRepository;
    private final LoyaltyProgramProviderRepository _loyaltyProgramProviderRepository;
    private final JavaMailSender _mailSender;
    private final CottageOwnerRepository _cottageOwnerRepository;
    private final BoatOwnerRepository _boatOwnerRepository;
    private final ReviewRepository _reviewRepository;
    private final ReviewFishingTripRepository _reviewFishingTripRepository;
    private final ComplaintRepository _complaintRepository;
    private final ComplaintFishingInstructorRepository _complaintFishingInstructorRepository;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder, RoleService roleService, UserService userService, UserRepository userRepository, CottageService cottageService, BoatService boatService, ClientRepository clientRepository, FishingInstructorRepository fishingInstructorRepository, FishingTripReservationRepository fishingTripReservationRepository, FishingTripQuickReservationRepository fishingTripQuickReservationRepository, ReservationRepository reservationRepository, ActionRepository actionRepository, CurrentSystemTaxPercentageRepository currentSystemTaxPercentageRepository, CurrentPointsClientGetsAfterReservationRepository currentPointsClientGetsAfterReservationRepository, CurrentPointsProviderGetsAfterReservationRepository currentPointsProviderGetsAfterReservationRepository, LoyaltyProgramClientRepository loyaltyProgramClientRepository, LoyaltyProgramProviderRepository loyaltyProgramProviderRepository, JavaMailSender mailSender, CottageOwnerRepository cottageOwnerRepository, BoatOwnerRepository boatOwnerRepository, ReviewRepository reviewRepository, ReviewFishingTripRepository reviewFishingTripRepository, ComplaintRepository complaintRepository, ComplaintFishingInstructorRepository complaintFishingInstructorRepository) {
        this._adminRepository = adminRepository;
        this._passwordEncoder = passwordEncoder;
        this._roleService = roleService;
        this._userService = userService;
        this._userRepository = userRepository;
        this._cottageService = cottageService;
        this._boatService = boatService;
        this._clientRepository = clientRepository;
        this._fishingInstructorRepository = fishingInstructorRepository;
        this._fishingTripQuickReservationRepository = fishingTripQuickReservationRepository;
        this._fishingTripReservationRepository = fishingTripReservationRepository;
        this._reservationRepository = reservationRepository;
        this._actionRepository = actionRepository;
        this._currentSystemTaxPercentageRepository = currentSystemTaxPercentageRepository;
        this._currentPointsClientGetsAfterReservationRepository = currentPointsClientGetsAfterReservationRepository;
        this._currentPointsProviderGetsAfterReservationRepository = currentPointsProviderGetsAfterReservationRepository;
        this._loyaltyProgramClientRepository = loyaltyProgramClientRepository;
        this._loyaltyProgramProviderRepository = loyaltyProgramProviderRepository;
        this._cottageOwnerRepository = cottageOwnerRepository;
        this._boatOwnerRepository = boatOwnerRepository;
        this._reviewRepository = reviewRepository;
        this._reviewFishingTripRepository = reviewFishingTripRepository;
        this._complaintRepository = complaintRepository;
        this._complaintFishingInstructorRepository = complaintFishingInstructorRepository;
        this._mailSender = mailSender;
    }

    @Override
    public Admin save(Admin admin) throws NonMainAdminRegisterOtherAdminException, EmailTakenException {
        throwExceptionIfNonMainAdminRegisterOtherAdmin();
        _userService.throwExceptionIfEmailExists(admin.getEmail());
        return saveFields(admin);
    }

    private void throwExceptionIfNonMainAdminRegisterOtherAdmin() throws NonMainAdminRegisterOtherAdminException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Admin admin = (Admin) _userService.findById(user.getId());
        if (!admin.isMainAdmin()) {
            throw new NonMainAdminRegisterOtherAdminException("Only main administrator can register other administrators!");
        }
    }

    private Admin saveFields(Admin admin) {
        admin.setPassword(_passwordEncoder.encode(admin.getPassword()));
        admin.setEnabled(true);
        admin.setMainAdmin(false);
        admin.setPasswordChanged(false);

        Role role = _roleService.findByName("ROLE_ADMIN");
        admin.setRole(role);

        return _adminRepository.save(admin);
    }

    @Transactional
    @Override
    public Admin changePersonalData(Admin admin) throws NotChangedPasswordException {
        Admin loggedInAdmin = getLoggedInAdmin();
        throwExceptionIfAdminDidntChangePassword(loggedInAdmin);
        return changePersonalDataFields(loggedInAdmin, admin);
    }

    private void throwExceptionIfAdminDidntChangePassword(Admin admin) throws NotChangedPasswordException {
        if (!admin.isPasswordChanged()) {
            throw new NotChangedPasswordException("Password not changed!");
        }
    }

    private Admin getLoggedInAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return (Admin) _userService.findById(user.getId());
    }

    private Admin changePersonalDataFields(Admin loggedInAdmin, Admin admin) {
        loggedInAdmin.setFirstName(admin.getFirstName());
        loggedInAdmin.setLastName(admin.getLastName());
        loggedInAdmin.setPhoneNumber(admin.getPhoneNumber());
        loggedInAdmin.getLocation().setCountryName(admin.getLocation().getCountryName());
        loggedInAdmin.getLocation().setCityName(admin.getLocation().getCityName());
        loggedInAdmin.getLocation().setStreetName(admin.getLocation().getStreetName());
        loggedInAdmin.getLocation().setLongitude(admin.getLocation().getLongitude());
        loggedInAdmin.getLocation().setLatitude(admin.getLocation().getLatitude());
        return _adminRepository.save(loggedInAdmin);
    }

    @Transactional
    @Override
    public void changePassword(Admin admin) throws ChangedPasswordException {
        Admin loggedInAdmin = getLoggedInAdmin();
        throwExceptionIfAdminChangedPassword(loggedInAdmin);
        changePasswordField(loggedInAdmin, admin);
    }

    private void throwExceptionIfAdminChangedPassword(Admin admin) throws ChangedPasswordException {
        if (admin.isPasswordChanged()) {
            throw new ChangedPasswordException("Password already changed!");
        }
    }

    private void changePasswordField(Admin loggedInAdmin, Admin admin) {
        loggedInAdmin.setPassword(_passwordEncoder.encode(admin.getPassword()));
        loggedInAdmin.setPasswordChanged(true);
        _adminRepository.save(loggedInAdmin);
    }

    @Transactional
    @Override
    public void deleteFishingInstructor(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException {
        _userService.throwExceptionIfUserDontExist(id);
        throwExceptionIfUserRoleNotFishingInstructor(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        FishingInstructor fishingInstructor = (FishingInstructor) _userService.findById(id);
        _userService.checkIfFishingInstructorDeletable(fishingInstructor);
        _userService.unlinkReferencesFishingInstructor(fishingInstructor);
        _userRepository.delete(fishingInstructor);
    }

    private void throwExceptionIfUserRoleNotFishingInstructor(Integer id) throws UnexpectedUserRoleException {
        if (!_userService.findById(id).getRole().getName().equals("ROLE_FISHING_INSTRUCTOR")) {
            throw new UnexpectedUserRoleException("User role isn't fishing instructor!");
        }
    }

    @Transactional
    @Override
    public void deleteCottageOwner(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException {
        _userService.throwExceptionIfUserDontExist(id);
        throwExceptionIfUserRoleNotCottageOwner(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        CottageOwner cottageOwner = (CottageOwner) _userService.findById(id);
        _userService.checkIfCottageOwnerDeletable(cottageOwner);
        _userService.unlinkReferencesCottageOwner(cottageOwner);
        _userRepository.delete(cottageOwner);
    }

    private void throwExceptionIfUserRoleNotCottageOwner(Integer id) throws UnexpectedUserRoleException {
        if (!_userService.findById(id).getRole().getName().equals("ROLE_COTTAGE_OWNER")) {
            throw new UnexpectedUserRoleException("User role isn't cottage owner!");
        }
    }

    @Transactional
    @Override
    public void deleteBoatOwner(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException {
        _userService.throwExceptionIfUserDontExist(id);
        throwExceptionIfUserRoleNotBoatOwner(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        BoatOwner boatOwner = (BoatOwner) _userService.findById(id);
        _userService.checkIfBoatOwnerDeletable(boatOwner);
        _userService.unlinkReferencesBoatOwner(boatOwner);
        _userRepository.delete(boatOwner);
    }

    private void throwExceptionIfUserRoleNotBoatOwner(Integer id) throws UnexpectedUserRoleException {
        if (!_userService.findById(id).getRole().getName().equals("ROLE_BOAT_OWNER")) {
            throw new UnexpectedUserRoleException("User role isn't boat owner!");
        }
    }

    @Transactional
    @Override
    public void deleteClient(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException {
        _userService.throwExceptionIfUserDontExist(id);
        throwExceptionIfUserRoleNotClient(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        Client client = (Client) _userService.findById(id);
        _userService.checkIfClientDeletable(client);
        _userService.unlinkReferencesClient(client);
        _userRepository.delete(client);
    }

    private void throwExceptionIfUserRoleNotClient(Integer id) throws UnexpectedUserRoleException {
        if (!_userService.findById(id).getRole().getName().equals("ROLE_CLIENT")) {
            throw new UnexpectedUserRoleException("User role isn't client!");
        }
    }

    @Transactional
    @Override
    public void deleteCottage(Integer id) throws NotChangedPasswordException, EntityNotFoundException, EntityNotDeletableException {
        _cottageService.throwExceptionIfCottageDontExist(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        _cottageService.checkIfCottageDeletable(id);
        _cottageService.unlinkReferencesAndDeleteCottage(id);
    }

    @Transactional
    @Override
    public void deleteBoat(Integer id) throws NotChangedPasswordException, EntityNotFoundException, EntityNotDeletableException {
        _boatService.throwExceptionIfBoatDontExist(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        _boatService.checkIfBoatDeletable(id);
        _boatService.unlinkReferencesAndDeleteBoat(id);
    }

    @Transactional
    @Override
    public void fishingReservationCommentaryAccept(Integer reservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException {
        throwExceptionIfFishingReservationDoesntExist(reservationId);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        FishingTripReservation fishingTripReservation = _fishingTripReservationRepository.getById(reservationId);
        throwExceptionIfCommentaryNotApprovable(fishingTripReservation);

        fishingTripReservation.getClient().setPenaltyPoints(fishingTripReservation.getClient().getPenaltyPoints() + 1);
        if (fishingTripReservation.getClient().getPenaltyPoints() == 3) {
            fishingTripReservation.getClient().setBanned(true);
        }
        _clientRepository.save(fishingTripReservation.getClient());

        fishingTripReservation.getOwnerCommentary().setAdminApproved(true);
        fishingTripReservation.getOwnerCommentary().setPenaltyGiven(true);
        _fishingTripReservationRepository.save(fishingTripReservation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(fishingTripReservation.getClient().getEmail());
        message.setSubject("Finished reservation report");
        message.setText("Instructor " + fishingTripReservation.getFishingTrip().getFishingInstructor().getFirstName() + " " + fishingTripReservation.getFishingTrip().getFishingInstructor().getLastName() + " gave you 1 penalty point!");
        _mailSender.send(message);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setFrom("marko76589@gmail.com");
        message2.setTo(fishingTripReservation.getFishingTrip().getFishingInstructor().getEmail());
        message2.setSubject("Finished reservation report");
        message2.setText("Client " + fishingTripReservation.getClient().getFirstName() + " " + fishingTripReservation.getClient().getLastName() + " was given 1 penalty point!");
        _mailSender.send(message2);
    }

    private void throwExceptionIfFishingReservationDoesntExist(Integer reservationId) throws EntityNotFoundException {
        Optional<FishingTripReservation> fishingTripReservation = _fishingTripReservationRepository.findById(reservationId);
        if (!fishingTripReservation.isPresent()) {
            throw new EntityNotFoundException("Reservation doesn't exist!");
        }
    }

    private void throwExceptionIfCommentaryNotApprovable(FishingTripReservation fishingTripReservation) throws CommentaryNotApprovableException {
        if (!(fishingTripReservation.getOwnerCommentary() != null && fishingTripReservation.getOwnerCommentary().isClientCame() && fishingTripReservation.getOwnerCommentary().isSanctionSuggested() && !fishingTripReservation.getOwnerCommentary().isAdminApproved())) {
            throw new CommentaryNotApprovableException("Commentary can't be approved by admin!");
        }
    }

    @Transactional
    @Override
    public void fishingReservationCommentaryDecline(Integer reservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException {
        throwExceptionIfFishingReservationDoesntExist(reservationId);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        FishingTripReservation fishingTripReservation = _fishingTripReservationRepository.getById(reservationId);
        throwExceptionIfCommentaryNotApprovable(fishingTripReservation);

        fishingTripReservation.getOwnerCommentary().setAdminApproved(true);
        fishingTripReservation.getOwnerCommentary().setPenaltyGiven(false);
        _fishingTripReservationRepository.save(fishingTripReservation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(fishingTripReservation.getFishingTrip().getFishingInstructor().getEmail());
        message.setSubject("Finished reservation report");
        message.setText("Client " + fishingTripReservation.getClient().getFirstName() + " " + fishingTripReservation.getClient().getLastName() + " wasn't given any penalty points!");
        _mailSender.send(message);
    }

    @Transactional
    @Override
    public void fishingQuickReservationCommentaryAccept(Integer quickReservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException {
        throwExceptionIfFishingQuickReservationDoesntExist(quickReservationId);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        FishingTripQuickReservation fishingTripQuickReservation = _fishingTripQuickReservationRepository.getById(quickReservationId);
        throwExceptionIfCommentaryNotApprovable(fishingTripQuickReservation);

        fishingTripQuickReservation.getClient().setPenaltyPoints(fishingTripQuickReservation.getClient().getPenaltyPoints() + 1);
        if (fishingTripQuickReservation.getClient().getPenaltyPoints() == 3) {
            fishingTripQuickReservation.getClient().setBanned(true);
        }
        _clientRepository.save(fishingTripQuickReservation.getClient());

        fishingTripQuickReservation.getOwnerCommentary().setAdminApproved(true);
        fishingTripQuickReservation.getOwnerCommentary().setPenaltyGiven(true);
        _fishingTripQuickReservationRepository.save(fishingTripQuickReservation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(fishingTripQuickReservation.getClient().getEmail());
        message.setSubject("Finished reservation report");
        message.setText("Instructor " + fishingTripQuickReservation.getFishingTrip().getFishingInstructor().getFirstName() + " " + fishingTripQuickReservation.getFishingTrip().getFishingInstructor().getLastName() + " gave you 1 penalty point!");
        _mailSender.send(message);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setFrom("marko76589@gmail.com");
        message2.setTo(fishingTripQuickReservation.getFishingTrip().getFishingInstructor().getEmail());
        message2.setSubject("Finished reservation report");
        message2.setText("Client " + fishingTripQuickReservation.getClient().getFirstName() + " " + fishingTripQuickReservation.getClient().getLastName() + " was given 1 penalty point!");
        _mailSender.send(message2);
    }

    private void throwExceptionIfFishingQuickReservationDoesntExist(Integer quickReservationId) throws EntityNotFoundException {
        Optional<FishingTripQuickReservation> fishingTripQuickReservation = _fishingTripQuickReservationRepository.findById(quickReservationId);
        if (!fishingTripQuickReservation.isPresent()) {
            throw new EntityNotFoundException("Quick reservation doesn't exist!");
        }
    }

    private void throwExceptionIfCommentaryNotApprovable(FishingTripQuickReservation fishingTripQuickReservation) throws CommentaryNotApprovableException {
        if (!(fishingTripQuickReservation.getOwnerCommentary() != null && fishingTripQuickReservation.getOwnerCommentary().isClientCame() && fishingTripQuickReservation.getOwnerCommentary().isSanctionSuggested() && !fishingTripQuickReservation.getOwnerCommentary().isAdminApproved())) {
            throw new CommentaryNotApprovableException("Commentary can't be approved by admin!");
        }
    }

    @Transactional
    @Override
    public void fishingQuickReservationCommentaryDecline(Integer quickReservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException {
        throwExceptionIfFishingQuickReservationDoesntExist(quickReservationId);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        FishingTripQuickReservation fishingTripQuickReservation = _fishingTripQuickReservationRepository.getById(quickReservationId);
        throwExceptionIfCommentaryNotApprovable(fishingTripQuickReservation);

        fishingTripQuickReservation.getOwnerCommentary().setAdminApproved(true);
        fishingTripQuickReservation.getOwnerCommentary().setPenaltyGiven(false);
        _fishingTripQuickReservationRepository.save(fishingTripQuickReservation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(fishingTripQuickReservation.getFishingTrip().getFishingInstructor().getEmail());
        message.setSubject("Finished reservation report");
        message.setText("Client " + fishingTripQuickReservation.getClient().getFirstName() + " " + fishingTripQuickReservation.getClient().getLastName() + " wasn't given any penalty points!");
        _mailSender.send(message);
    }

    @Transactional
    @Override
    public void reservationCommentaryAccept(Integer reservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException {
        throwExceptionIfReservationDoesntExist(reservationId);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        Reservation reservation = _reservationRepository.getById(reservationId);
        throwExceptionIfCommentaryNotApprovable(reservation);

        reservation.getClient().setPenaltyPoints(reservation.getClient().getPenaltyPoints() + 1);
        if (reservation.getClient().getPenaltyPoints() == 3) {
            reservation.getClient().setBanned(true);
        }
        _clientRepository.save(reservation.getClient());

        reservation.getOwnerCommentary().setAdminApproved(true);
        reservation.getOwnerCommentary().setPenaltyGiven(true);
        _reservationRepository.save(reservation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(reservation.getClient().getEmail());
        message.setSubject("Finished reservation report");
        if (reservation instanceof CottageReservation) {
            message.setText("Cottage owner " + ((CottageReservation) reservation).getCottage().getCottageOwner().getFirstName() + " " + ((CottageReservation) reservation).getCottage().getCottageOwner().getLastName() + " gave you 1 penalty point!");
        } else if (reservation instanceof BoatReservation) {
            message.setText("Boat owner " + ((BoatReservation) reservation).getBoat().getBoatOwner().getFirstName() + " " + ((BoatReservation) reservation).getBoat().getBoatOwner().getLastName() + " gave you 1 penalty point!");
        }
        _mailSender.send(message);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setFrom("marko76589@gmail.com");
        if (reservation instanceof CottageReservation) {
            message2.setTo(((CottageReservation) reservation).getCottage().getCottageOwner().getEmail());
        } else if (reservation instanceof BoatReservation) {
            message2.setTo(((BoatReservation) reservation).getBoat().getBoatOwner().getEmail());
        }
        message2.setSubject("Finished reservation report");
        message2.setText("Client " + reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName() + " was given 1 penalty point!");
        _mailSender.send(message2);
    }

    private void throwExceptionIfReservationDoesntExist(Integer reservationId) throws EntityNotFoundException {
        Optional<Reservation> reservation = _reservationRepository.findById(reservationId);
        if (!reservation.isPresent()) {
            throw new EntityNotFoundException("Reservation doesn't exist!");
        }
    }

    private void throwExceptionIfCommentaryNotApprovable(Reservation reservation) throws CommentaryNotApprovableException {
        if (!(reservation.getOwnerCommentary() != null && reservation.getOwnerCommentary().isClientCame() && reservation.getOwnerCommentary().isSanctionSuggested() && !reservation.getOwnerCommentary().isAdminApproved())) {
            throw new CommentaryNotApprovableException("Commentary can't be approved by admin!");
        }
    }

    @Transactional
    @Override
    public void reservationCommentaryDecline(Integer reservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException {
        throwExceptionIfReservationDoesntExist(reservationId);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        Reservation reservation = _reservationRepository.getById(reservationId);
        throwExceptionIfCommentaryNotApprovable(reservation);

        reservation.getOwnerCommentary().setAdminApproved(true);
        reservation.getOwnerCommentary().setPenaltyGiven(false);
        _reservationRepository.save(reservation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        if (reservation instanceof CottageReservation) {
            message.setTo(((CottageReservation) reservation).getCottage().getCottageOwner().getEmail());
        } else if (reservation instanceof BoatReservation) {
            message.setTo(((BoatReservation) reservation).getBoat().getBoatOwner().getEmail());
        }
        message.setSubject("Finished reservation report");
        message.setText("Client " + reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName() + " wasn't given any penalty points!");
        _mailSender.send(message);
    }

    @Transactional
    @Override
    public void quickReservationCommentaryAccept(Integer quickReservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException {
        throwExceptionIfQuickReservationDoesntExist(quickReservationId);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        Action quickReservation = _actionRepository.getById(quickReservationId);
        throwExceptionIfCommentaryNotApprovable(quickReservation);

        quickReservation.getClient().setPenaltyPoints(quickReservation.getClient().getPenaltyPoints() + 1);
        if (quickReservation.getClient().getPenaltyPoints() == 3) {
            quickReservation.getClient().setBanned(true);
        }
        _clientRepository.save(quickReservation.getClient());

        quickReservation.getOwnerCommentary().setAdminApproved(true);
        quickReservation.getOwnerCommentary().setPenaltyGiven(true);
        _actionRepository.save(quickReservation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(quickReservation.getClient().getEmail());
        message.setSubject("Finished reservation report");
        if (quickReservation instanceof CottageAction) {
            message.setText("Cottage owner " + ((CottageAction) quickReservation).getCottage().getCottageOwner().getFirstName() + " " + ((CottageAction) quickReservation).getCottage().getCottageOwner().getLastName() + " gave you 1 penalty point!");
        } else if (quickReservation instanceof BoatAction) {
            message.setText("Boat owner " + ((BoatAction) quickReservation).getBoat().getBoatOwner().getFirstName() + " " + ((BoatAction) quickReservation).getBoat().getBoatOwner().getLastName() + " gave you 1 penalty point!");
        }
        _mailSender.send(message);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setFrom("marko76589@gmail.com");
        if (quickReservation instanceof CottageAction) {
            message2.setTo(((CottageAction) quickReservation).getCottage().getCottageOwner().getEmail());
        } else if (quickReservation instanceof BoatAction) {
            message2.setTo(((BoatAction) quickReservation).getBoat().getBoatOwner().getEmail());
        }
        message2.setSubject("Finished reservation report");
        message2.setText("Client " + quickReservation.getClient().getFirstName() + " " + quickReservation.getClient().getLastName() + " was given 1 penalty point!");
        _mailSender.send(message2);
    }

    private void throwExceptionIfQuickReservationDoesntExist(Integer quickReservationId) throws EntityNotFoundException {
        Optional<Action> quickReservation = _actionRepository.findById(quickReservationId);
        if (!quickReservation.isPresent()) {
            throw new EntityNotFoundException("Quick reservation doesn't exist!");
        }
    }

    private void throwExceptionIfCommentaryNotApprovable(Action quickReservation) throws CommentaryNotApprovableException {
        if (!(quickReservation.getOwnerCommentary() != null && quickReservation.getOwnerCommentary().isClientCame() && quickReservation.getOwnerCommentary().isSanctionSuggested() && !quickReservation.getOwnerCommentary().isAdminApproved())) {
            throw new CommentaryNotApprovableException("Commentary can't be approved by admin!");
        }
    }

    @Transactional
    @Override
    public void quickReservationCommentaryDecline(Integer quickReservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException {
        throwExceptionIfQuickReservationDoesntExist(quickReservationId);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        Action quickReservation = _actionRepository.getById(quickReservationId);
        throwExceptionIfCommentaryNotApprovable(quickReservation);

        quickReservation.getOwnerCommentary().setAdminApproved(true);
        quickReservation.getOwnerCommentary().setPenaltyGiven(false);
        _actionRepository.save(quickReservation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        if (quickReservation instanceof CottageAction) {
            message.setTo(((CottageAction) quickReservation).getCottage().getCottageOwner().getEmail());
        } else if (quickReservation instanceof BoatAction) {
            message.setTo(((BoatAction) quickReservation).getBoat().getBoatOwner().getEmail());
        }
        message.setSubject("Finished reservation report");
        message.setText("Client " + quickReservation.getClient().getFirstName() + " " + quickReservation.getClient().getLastName() + " wasn't given any penalty points!");
        _mailSender.send(message);
    }

    @Transactional
    @Override
    public void changeCurrentSystemTaxPercentage(CurrentSystemTaxPercentage currentSystemTaxPercentage) throws NotChangedPasswordException {
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        CurrentSystemTaxPercentage currentSystemTaxPercentageFromDatabase = _currentSystemTaxPercentageRepository.getById(1);
        currentSystemTaxPercentageFromDatabase.setCurrentSystemTaxPercentage(currentSystemTaxPercentage.getCurrentSystemTaxPercentage());
        _currentSystemTaxPercentageRepository.save(currentSystemTaxPercentageFromDatabase);
    }
    
    @Transactional
    @Override
    public void currentPointsClientGetsAfterReservation(CurrentPointsClientGetsAfterReservation currentPointsClientGetsAfterReservation) throws NotChangedPasswordException {
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        CurrentPointsClientGetsAfterReservation currentPointsClientGetsAfterReservationFromDatabase = _currentPointsClientGetsAfterReservationRepository.getById(1);
        currentPointsClientGetsAfterReservationFromDatabase.setCurrentPointsClientGetsAfterReservation(currentPointsClientGetsAfterReservation.getCurrentPointsClientGetsAfterReservation());
        _currentPointsClientGetsAfterReservationRepository.save(currentPointsClientGetsAfterReservationFromDatabase);
    }

    @Transactional
    @Override
    public void currentPointsProviderGetsAfterReservation(CurrentPointsProviderGetsAfterReservation currentPointsProviderGetsAfterReservation) throws NotChangedPasswordException {
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        CurrentPointsProviderGetsAfterReservation currentPointsProviderGetsAfterReservationFromDatabase = _currentPointsProviderGetsAfterReservationRepository.getById(1);
        currentPointsProviderGetsAfterReservationFromDatabase.setCurrentPointsProviderGetsAfterReservation(currentPointsProviderGetsAfterReservation.getCurrentPointsProviderGetsAfterReservation());
        _currentPointsProviderGetsAfterReservationRepository.save(currentPointsProviderGetsAfterReservationFromDatabase);
    }

    @Transactional
    @Override
    public void changeClientPointsNeededForLoyaltyProgramCategory(Integer id, LoyaltyProgramClient loyaltyProgramClient) throws NotChangedPasswordException, EntityNotUpdateableException {
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        throwExceptionIfChangingPointsForLoyaltyProgramCategoryThatIsRegularOrDoesntExist(id);
        throwExceptionIfClientPointsNeededForLoyaltyProgramSilverCategoryHigherThanGoldCategory(id, loyaltyProgramClient);

        LoyaltyProgramClient loyaltyProgramClientFromDatabase = _loyaltyProgramClientRepository.getById(id);
        loyaltyProgramClientFromDatabase.setPointsNeeded(loyaltyProgramClient.getPointsNeeded());
        _loyaltyProgramClientRepository.save(loyaltyProgramClientFromDatabase);

        List<LoyaltyProgramClient> loyaltyProgramClients = _loyaltyProgramClientRepository.findAll();
        Collections.sort(loyaltyProgramClients, Comparator.comparing(LoyaltyProgramClient::getPointsNeeded));

        for (Client client : _clientRepository.findAll()) {
            for (LoyaltyProgramClient lpc : loyaltyProgramClients) {
                if (client.getLoyaltyPoints() >= lpc.getPointsNeeded()) {
                    client.setCategory(lpc);
                }
            }
            _clientRepository.save(client);
        }
    }

    private void throwExceptionIfChangingPointsForLoyaltyProgramCategoryThatIsRegularOrDoesntExist(Integer id) throws EntityNotUpdateableException {
        if (id != 2 && id != 3) {
            throw new EntityNotUpdateableException("Changing points needed for regular category in loyalty program not allowed or category doesn't exist!");
        }
    }

    private void throwExceptionIfClientPointsNeededForLoyaltyProgramSilverCategoryHigherThanGoldCategory(Integer id, LoyaltyProgramClient loyaltyProgramClient) throws EntityNotUpdateableException {
        if ((id == 2 && loyaltyProgramClient.getPointsNeeded() >= _loyaltyProgramClientRepository.getById(3).getPointsNeeded()) || (id == 3 && loyaltyProgramClient.getPointsNeeded() <= _loyaltyProgramClientRepository.getById(2).getPointsNeeded())) {
            throw new EntityNotUpdateableException("Silver loyalty program category can't have higher or equal points needed than gold category!");
        }
    }

    @Transactional
    @Override
    public void changeProviderPointsNeededForLoyaltyProgramCategory(Integer id, LoyaltyProgramProvider loyaltyProgramProvider) throws NotChangedPasswordException, EntityNotUpdateableException {
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());
        throwExceptionIfChangingPointsForLoyaltyProgramCategoryThatIsRegularOrDoesntExist(id);
        throwExceptionIfProviderPointsNeededForLoyaltyProgramSilverCategoryHigherThanGoldCategory(id, loyaltyProgramProvider);

        LoyaltyProgramProvider loyaltyProgramProviderFromDatabase = _loyaltyProgramProviderRepository.getById(id);
        loyaltyProgramProviderFromDatabase.setPointsNeeded(loyaltyProgramProvider.getPointsNeeded());
        _loyaltyProgramProviderRepository.save(loyaltyProgramProviderFromDatabase);

        List<LoyaltyProgramProvider> loyaltyProgramProviders = _loyaltyProgramProviderRepository.findAll();
        Collections.sort(loyaltyProgramProviders, Comparator.comparing(LoyaltyProgramProvider::getPointsNeeded));

        for (FishingInstructor fishingInstructor : _fishingInstructorRepository.findAll()) {
            for (LoyaltyProgramProvider lpp : loyaltyProgramProviders) {
                if (fishingInstructor.getLoyaltyPoints() >= lpp.getPointsNeeded()) {
                    fishingInstructor.setCategory(lpp);
                }
            }
            _fishingInstructorRepository.save(fishingInstructor);
        }

        for (CottageOwner owner : _cottageOwnerRepository.findAll()){
            for (LoyaltyProgramProvider lpp : loyaltyProgramProviders){
                if (owner.getLoyaltyPoints() >= lpp.getPointsNeeded()){
                    owner.setCategory(lpp);
                }
            }
            _cottageOwnerRepository.save(owner);
        }

        for (BoatOwner owner : _boatOwnerRepository.findAll()){
            for (LoyaltyProgramProvider lpp : loyaltyProgramProviders){
                if (owner.getLoyaltyPoints() >= lpp.getPointsNeeded()){
                    owner.setCategory(lpp);
                }
            }
            _boatOwnerRepository.save(owner);
        }
    }

    private void throwExceptionIfProviderPointsNeededForLoyaltyProgramSilverCategoryHigherThanGoldCategory(Integer id, LoyaltyProgramProvider loyaltyProgramProvider) throws EntityNotUpdateableException {
        if ((id == 2 && loyaltyProgramProvider.getPointsNeeded() >= _loyaltyProgramProviderRepository.getById(3).getPointsNeeded()) || (id == 3 && loyaltyProgramProvider.getPointsNeeded() <= _loyaltyProgramProviderRepository.getById(2).getPointsNeeded())) {
            throw new EntityNotUpdateableException("Silver loyalty program category can't have higher points needed requirement than gold category!");
        }
    }

    @Transactional
    @Override
    public void respondToComplaint(Integer id, ComplaintResponse complaintResponse) throws EntityNotFoundException {
        throwExceptionIfComplaintDoesntExist(id);

        Complaint complaint = _complaintRepository.getById(id);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(complaint.getClient().getEmail());
        message.setSubject("Complaint response");
        message.setText(complaintResponse.getResponseToClient());
        _mailSender.send(message);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setFrom("marko76589@gmail.com");
        if (complaint.getSaleEntity() instanceof Cottage) {
            message2.setTo(((Cottage) complaint.getSaleEntity()).getCottageOwner().getEmail());
        } else if (complaint.getSaleEntity() instanceof Boat) {
            message2.setTo(((Boat) complaint.getSaleEntity()).getBoatOwner().getEmail());
        }
        message2.setSubject("Complaint response");
        message2.setText(complaintResponse.getResponseToProvider());
        _mailSender.send(message2);

        _complaintRepository.delete(complaint);
    }

    private void throwExceptionIfComplaintDoesntExist(Integer id) throws EntityNotFoundException {
        Optional<Complaint> complaint = _complaintRepository.findById(id);
        if (!complaint.isPresent()) {
            throw new EntityNotFoundException("Complaint doesn't exist!");
        }
    }

    @Transactional
    @Override
    public void respondToComplaintFishingInstructor(Integer id, ComplaintResponse complaintResponse) throws EntityNotFoundException {
        throwExceptionIfComplaintFishingInstructorDoesntExist(id);

        ComplaintFishingInstructor complaint = _complaintFishingInstructorRepository.getById(id);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(complaint.getClient().getEmail());
        message.setSubject("Complaint response");
        message.setText(complaintResponse.getResponseToClient());
        _mailSender.send(message);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setFrom("marko76589@gmail.com");
        message2.setTo(complaint.getFishingInstructor().getEmail());
        message2.setSubject("Complaint response");
        message2.setText(complaintResponse.getResponseToProvider());
        _mailSender.send(message2);

        _complaintFishingInstructorRepository.delete(complaint);
    }

    private void throwExceptionIfComplaintFishingInstructorDoesntExist(Integer id) throws EntityNotFoundException {
        Optional<ComplaintFishingInstructor> complaint = _complaintFishingInstructorRepository.findById(id);
        if (!complaint.isPresent()) {
            throw new EntityNotFoundException("Complaint doesn't exist!");
        }
    }

    @Transactional
    @Override
    public void acceptReview(Integer id) throws EntityNotFoundException, EntityNotUpdateableException {
        throwExceptionIfReviewDoesntExist(id);
        throwExceptionIfReviewAlreadyApproved(id);

        Review review = _reviewRepository.getById(id);
        review.setApproved(true);
        _reviewRepository.save(review);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        if (review.getSaleEntity() instanceof Cottage) {
            message.setTo(((Cottage) review.getSaleEntity()).getCottageOwner().getEmail());
        } else if (review.getSaleEntity() instanceof Boat) {
            message.setTo(((Boat) review.getSaleEntity()).getBoatOwner().getEmail());
        }
        message.setSubject("Review added");
        message.setText("You have a new review for: " + review.getSaleEntity().getName());
        _mailSender.send(message);
    }

    private void throwExceptionIfReviewDoesntExist(Integer id) throws EntityNotFoundException {
        Optional<Review> review = _reviewRepository.findById(id);
        if (!review.isPresent()) {
            throw new EntityNotFoundException("Review doesn't exist!");
        }
    }

    private void throwExceptionIfReviewAlreadyApproved(Integer id) throws EntityNotUpdateableException {
        Review review = _reviewRepository.getById(id);
        if (review.isApproved()) {
            throw new EntityNotUpdateableException("Review already approved!");
        }
    }

    @Transactional
    @Override
    public void acceptReviewFishingTrip(Integer id) throws EntityNotFoundException, EntityNotUpdateableException {
        throwExceptionIfReviewFishingTripDoesntExist(id);
        throwExceptionIfReviewFishingTripAlreadyApproved(id);

        ReviewFishingTrip review = _reviewFishingTripRepository.getById(id);
        review.setApproved(true);
        _reviewFishingTripRepository.save(review);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(review.getFishingTrip().getFishingInstructor().getEmail());
        message.setSubject("Review added");
        message.setText("You have a new review for fishing trip: " + review.getFishingTrip().getName());
        _mailSender.send(message);
    }

    private void throwExceptionIfReviewFishingTripDoesntExist(Integer id) throws EntityNotFoundException {
        Optional<ReviewFishingTrip> review = _reviewFishingTripRepository.findById(id);
        if (!review.isPresent()) {
            throw new EntityNotFoundException("Review doesn't exist!");
        }
    }

    private void throwExceptionIfReviewFishingTripAlreadyApproved(Integer id) throws EntityNotUpdateableException {
        ReviewFishingTrip review = _reviewFishingTripRepository.getById(id);
        if (review.isApproved()) {
            throw new EntityNotUpdateableException("Review already approved!");
        }
    }

    @Transactional
    @Override
    public void declineReview(Integer id) throws EntityNotFoundException {
        throwExceptionIfReviewDoesntExist(id);

        _reviewRepository.delete(_reviewRepository.getById(id));
    }

    @Transactional
    @Override
    public void declineReviewFishingTrip(Integer id) throws EntityNotFoundException {
        throwExceptionIfReviewFishingTripDoesntExist(id);

        _reviewFishingTripRepository.delete(_reviewFishingTripRepository.getById(id));
    }

    @Scheduled(cron="0 0 0 1 1/1 *")
    public void unbanAllClientsAndResetTheirPenaltyPoints() {
        List<Client> allClients = _clientRepository.findAll();
        for (Client client : allClients) {
            client.setBanned(false);
            client.setPenaltyPoints(0);
            _clientRepository.save(client);
        }
    }
}
