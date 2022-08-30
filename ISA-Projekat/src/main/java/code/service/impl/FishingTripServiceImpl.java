package code.service.impl;

import code.dto.fishing_trip.FishingInstructorFishingTripTableGetDto;
import code.dto.fishing_trip.FishingInstructorReservationTableGetDto;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.EntityNotOwnedException;
import code.exceptions.entities.ReservationOrActionAlreadyCommented;
import code.exceptions.entities.ReservationOrActionNotFinishedException;
import code.exceptions.fishing_trip.*;
import code.exceptions.fishing_trip.quick_reservation.*;
import code.exceptions.fishing_trip.reservation.*;
import code.model.*;
import code.model.base.*;
import code.model.cottage.Cottage;
import code.repository.*;
import code.service.FishingTripService;
import code.service.UserService;
import code.utils.FileUploadUtil;
import com.google.common.collect.Lists;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class FishingTripServiceImpl implements FishingTripService {
    private final String FISHING_TRIP_PICTURE_DIRECTORY = "fishing_trip_images";

    private final FishingInstructorRepository _fishingInstructorRepository;
    private final FishingTripRepository _fishingTripRepository;
    private final FishingTripPictureRepository _fishingTripPictureRepository;
    private final FishingInstructorAvailablePeriodRepository _fishingInstructorAvailablePeriodRepository;
    private final FishingTripQuickReservationRepository _fishingTripQuickReservationRepository;
    private final FishingTripReservationRepository _fishingTripReservationRepository;
    private final ClientRepository _clientRepository;
    private final UserService _userService;
    private final ReservationRepository _reservationRepository;
    private final ActionRepository _actionRepository;
    private final CurrentSystemTaxPercentageRepository _currentSystemTaxPercentageRepository;
    private final IncomeRecordRepository _incomeRecordRepository;
    private final CurrentPointsClientGetsAfterReservationRepository _currentPointsClientGetsAfterReservationRepository;
    private final CurrentPointsProviderGetsAfterReservationRepository _currentPointsProviderGetsAfterReservationRepository;
    private final LoyaltyProgramClientRepository _loyaltyProgramClientRepository;
    private final LoyaltyProgramProviderRepository _loyaltyProgramProviderRepository;
    private final JavaMailSender _mailSender;

    public FishingTripServiceImpl(FishingInstructorRepository fishingInstructorRepository, FishingTripPictureRepository fishingTripPictureRepository, FishingTripRepository fishingTripRepository, FishingInstructorAvailablePeriodRepository fishingInstructorAvailablePeriodRepository, FishingTripQuickReservationRepository fishingTripQuickReservationRepository, FishingTripReservationRepository fishingTripReservationRepository, ClientRepository clientRepository, UserService userService, ReservationRepository reservationRepository, ActionRepository actionRepository, CurrentSystemTaxPercentageRepository currentSystemTaxPercentageRepository, IncomeRecordRepository incomeRecordRepository, CurrentPointsClientGetsAfterReservationRepository currentPointsClientGetsAfterReservationRepository, CurrentPointsProviderGetsAfterReservationRepository currentPointsProviderGetsAfterReservationRepository, LoyaltyProgramClientRepository loyaltyProgramClientRepository, LoyaltyProgramProviderRepository loyaltyProgramProviderRepository, JavaMailSender mailSender) {
        this._fishingInstructorRepository = fishingInstructorRepository;
        this._fishingTripPictureRepository = fishingTripPictureRepository;
        this._fishingTripRepository = fishingTripRepository;
        this._fishingInstructorAvailablePeriodRepository = fishingInstructorAvailablePeriodRepository;
        this._fishingTripQuickReservationRepository = fishingTripQuickReservationRepository;
        this._fishingTripReservationRepository = fishingTripReservationRepository;
        this._userService = userService;
        this._clientRepository = clientRepository;
        this._reservationRepository = reservationRepository;
        this._actionRepository = actionRepository;
        this._currentSystemTaxPercentageRepository = currentSystemTaxPercentageRepository;
        this._incomeRecordRepository = incomeRecordRepository;
        this._currentPointsClientGetsAfterReservationRepository = currentPointsClientGetsAfterReservationRepository;
        this._currentPointsProviderGetsAfterReservationRepository = currentPointsProviderGetsAfterReservationRepository;
        this._loyaltyProgramClientRepository = loyaltyProgramClientRepository;
        this._loyaltyProgramProviderRepository = loyaltyProgramProviderRepository;
        this._mailSender = mailSender;
    }

    @Override
    public FishingTrip save(FishingTrip fishingTrip) {
        setInstructor(fishingTrip);
        return _fishingTripRepository.save(fishingTrip);
    }

    @Override
    public FishingTrip getFishingTrip(Integer id) throws EntityNotFoundException {
        FishingTrip fishingTrip = _fishingTripRepository.findById(id).orElse(null);
        if(fishingTrip == null) {
            throw new EntityNotFoundException("Fishing trip doesn't exist!");
        }
        return fishingTrip;
    }

    @Transactional
    @Override
    public FishingTrip edit(FishingTrip fishingTrip) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, FishingTripHasQuickReservationWithClientException, FishingTripHasReservationException {
        throwExceptionIfFishingTripNotFound(fishingTrip.getId());
        throwExceptionIfEditAnotherInstructorFishingTrip(fishingTrip.getId());
        throwExceptionIfFishingTripHasQuickReservationWithClient(fishingTrip.getId());
        throwExceptionIfFishingTripHasReservation(fishingTrip.getId());
        setInstructor(fishingTrip);
        setLocationId(fishingTrip);
        fishingTrip.setPictures(new HashSet<>(_fishingTripPictureRepository.findByFishingTrip(fishingTrip.getId())));
        return _fishingTripRepository.save(fishingTrip);
    }

    private void throwExceptionIfFishingTripNotFound(Integer fishingTripId) throws FishingTripNotFoundException {
        Optional<FishingTrip> fishingTrip = _fishingTripRepository.findById(fishingTripId);
        if (!fishingTrip.isPresent()) {
            throw new FishingTripNotFoundException("Fishing trip not found!");
        }
    }

    private void throwExceptionIfEditAnotherInstructorFishingTrip(Integer fishingTripId) throws EditAnotherInstructorFishingTripException {
        FishingTrip ft = _fishingTripRepository.getById(fishingTripId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        FishingInstructor fishingInstructor = (FishingInstructor) _userService.findById(user.getId());
        if (fishingInstructor.getId() != ft.getFishingInstructor().getId()) {
            throw new EditAnotherInstructorFishingTripException("You can't edit another instructor's fishing trip!");
        }
    }

    private void throwExceptionIfFishingTripHasQuickReservationWithClient(Integer fishingTripId) throws FishingTripHasQuickReservationWithClientException {
        List<FishingTripQuickReservation> fishingTripQuickReservations = _fishingTripQuickReservationRepository.findByFishingTripId(fishingTripId);
        for (FishingTripQuickReservation fishingTripQuickReservation : fishingTripQuickReservations) {
            if (fishingTripQuickReservation.getClient() != null && fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays()).isAfter(LocalDate.now())) {
                throw new FishingTripHasQuickReservationWithClientException("You can't edit fishing trip that is reserved!");
            }
        }
    }

    private void throwExceptionIfFishingTripHasReservation(Integer fishingTripId) throws FishingTripHasReservationException {
        List<FishingTripReservation> fishingTripReservations = _fishingTripReservationRepository.findByFishingTripId(fishingTripId);
        for (FishingTripReservation fishingTripReservation : fishingTripReservations) {
            if (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays()).isAfter(LocalDate.now())) {
                throw new FishingTripHasReservationException("You can't edit fishing trip that is reserved!");
            }
        }
    }

    private void setInstructor(FishingTrip fishingTrip) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        FishingInstructor fishingInstructor = (FishingInstructor) _userService.findById(user.getId());
        fishingTrip.setFishingInstructor(fishingInstructor);
    }

    private void setLocationId(FishingTrip fishingTrip) {
        FishingTrip ft = _fishingTripRepository.getById(fishingTrip.getId());
        fishingTrip.getLocation().setId(ft.getLocation().getId());
    }

    @Transactional
    @Override
    public void editPictures(Integer id, MultipartFile[] pictures) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, IOException, FishingTripHasQuickReservationWithClientException, FishingTripHasReservationException {
        throwExceptionIfFishingTripNotFound(id);
        throwExceptionIfEditAnotherInstructorFishingTrip(id);
        throwExceptionIfFishingTripHasQuickReservationWithClient(id);
        throwExceptionIfFishingTripHasReservation(id);

        String fishingTripPicturesDirectory = "fishing_trip_pictures";
        FishingTrip fishingTrip = _fishingTripRepository.getById(id);
        deletePreviousPictures(fishingTripPicturesDirectory, fishingTrip);
        FishingTrip fishingTripFromDatabase = saveNewPicturesToDatabase(pictures, fishingTrip);
        saveNewPicturesToDirectory(fishingTripFromDatabase, pictures, fishingTripPicturesDirectory);
    }

    private void deletePreviousPictures(String fishingTripPicturesDirectory, FishingTrip fishingTrip) {
        List<FishingTripPicture> fishingTripPictures = _fishingTripPictureRepository.findByFishingTrip(fishingTrip.getId());
        for (FishingTripPicture fishingTripPicture : fishingTripPictures) {
            fishingTrip.getPictures().remove(fishingTripPicture);
            fishingTripPicture.setFishingTrip(null);
            FileUploadUtil.deleteFile(fishingTripPicturesDirectory, fishingTripPicture.getId() + "_" + fishingTripPicture.getName());
        }
    }

    private FishingTrip saveNewPicturesToDatabase(MultipartFile[] pictures, FishingTrip fishingTrip) {
        if (pictures != null) {
            for (MultipartFile picture : pictures) {
                String pictureFileName = StringUtils.cleanPath(picture.getOriginalFilename());

                FishingTripPicture fishingTripPicture = new FishingTripPicture();
                fishingTripPicture.setName(pictureFileName);
                fishingTripPicture.setFishingTrip(fishingTrip);
                fishingTrip.getPictures().add(fishingTripPicture);
            }
        }

        return _fishingTripRepository.save(fishingTrip);
    }

    private void saveNewPicturesToDirectory(FishingTrip fishingTripFromDatabase, MultipartFile[] pictures, String fishingTripPicturesDirectory) throws IOException {
        int i = 0;
        for (FishingTripPicture fishingTripPicture : fishingTripFromDatabase.getPictures()) {
            MultipartFile picture = pictures[i++];
            FileUploadUtil.saveFile(fishingTripPicturesDirectory, fishingTripPicture.getId() + "_" + fishingTripPicture.getName(), picture);
        }
    }

    @Transactional
    @Override
    public void deleteFishingTrip(Integer id) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, FishingTripHasQuickReservationWithClientException, FishingTripHasReservationException {
        throwExceptionIfFishingTripNotFound(id);
        throwExceptionIfEditAnotherInstructorFishingTrip(id);
        throwExceptionIfFishingTripHasQuickReservationWithClient(id);
        throwExceptionIfFishingTripHasReservation(id);
        deleteFishingTripLogic(id);
    }

    private void deleteFishingTripLogic(Integer id) {
        deleteFishingTripReservations(id);
        unlinkFishingTripQuickReservationsFromClient(id);
        FishingTrip fishingTrip = _fishingTripRepository.getById(id);
        deleteFishingTripPictures(fishingTrip);
        FishingInstructor fishingInstructor = fishingTrip.getFishingInstructor();
        fishingInstructor.getFishingTrips().remove(fishingTrip);
        fishingTrip.setFishingInstructor(null);
        _fishingTripRepository.delete(_fishingTripRepository.getById(id));
    }

    private void deleteFishingTripReservations(Integer id) {
        List<FishingTripReservation> fishingTripReservations = _fishingTripReservationRepository.findByFishingTripId(id);
        for (FishingTripReservation fishingTripReservation : fishingTripReservations) {
            fishingTripReservation.setFishingTrip(null);

            fishingTripReservation.getClient().getFishingTripReservations().remove(fishingTripReservation);
            fishingTripReservation.setClient(null);

            _fishingTripReservationRepository.delete(fishingTripReservation);
        }
    }

    private void unlinkFishingTripQuickReservationsFromClient(Integer id) {
        List<FishingTripQuickReservation> fishingTripQuickReservations = _fishingTripQuickReservationRepository.findByFishingTripId(id);
        for (FishingTripQuickReservation fishingTripQuickReservation : fishingTripQuickReservations) {
            if (fishingTripQuickReservation.getClient() != null) {
                fishingTripQuickReservation.getClient().getFishingTripQuickReservations().remove(fishingTripQuickReservation);
                fishingTripQuickReservation.setClient(null);
            }
        }
    }

    public void deleteFishingTripPictures(FishingTrip fishingTrip) {
        String fishingTripPicturesDirectory = "fishing_trip_pictures";
        List<FishingTripPicture> fishingTripPictures = _fishingTripPictureRepository.findByFishingTrip(fishingTrip.getId());
        for (FishingTripPicture fishingTripPicture : fishingTripPictures) {
            FileUploadUtil.deleteFile(fishingTripPicturesDirectory, fishingTripPicture.getId() + "_" + fishingTripPicture.getName());
        }
    }

    @Transactional
    @Override
    public void addQuickReservation(Integer id, FishingTripQuickReservation fishingTripQuickReservation) throws AddReservationToAnotherInstructorFishingTripException, FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException, ReservationStartDateInPastException, ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException, FishingTripReservationTagsDontContainReservationTagException, NoAvailablePeriodForReservationException, FishingTripNotFoundException, InstructorBusyDuringReservationException {
        FishingInstructor loggedInInstructor = getLoggedInFishingInstructor();
        throwExceptionIfFishingTripNotFound(id);
        FishingTrip fishingTrip = _fishingTripRepository.getById(id);
        throwExceptionIfAddQuickReservationToAnotherInstructorFishingTrip(loggedInInstructor, fishingTrip);
        throwExceptionIfFishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeople(fishingTripQuickReservation, fishingTrip);
        throwExceptionIfQuickReservationStartDateInPast(fishingTripQuickReservation);
        throwExceptionIfValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDate(fishingTripQuickReservation);
        throwExceptionIfFishingTripReservationTagsDontContainQuickReservationTag(fishingTripQuickReservation, fishingTrip);
        throwExceptionIfNoAvailablePeriodForQuickReservation(loggedInInstructor, fishingTripQuickReservation);
        throwExceptionIfInstructorBusyDuringReservation(loggedInInstructor, fishingTripQuickReservation);
        fishingTripQuickReservation.setFishingTrip(fishingTrip);
        fishingTripQuickReservation.setSystemTaxPercentage(_currentSystemTaxPercentageRepository.getById(1).getCurrentSystemTaxPercentage() - loggedInInstructor.getCategory().getLesserSystemTaxPercentage());
        fishingTripQuickReservation.setLoyaltyPointsGiven(false);
        _fishingTripQuickReservationRepository.save(fishingTripQuickReservation);
        sendQuickReservationCreatedMailToSubscribers(loggedInInstructor);
    }

    private FishingInstructor getLoggedInFishingInstructor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return (FishingInstructor) _userService.findById(user.getId());
    }

    private void throwExceptionIfAddQuickReservationToAnotherInstructorFishingTrip(FishingInstructor loggedInInstructor, FishingTrip fishingTrip) throws AddReservationToAnotherInstructorFishingTripException {
        if (fishingTrip.getFishingInstructor().getId() != loggedInInstructor.getId()) {
            throw new AddReservationToAnotherInstructorFishingTripException("You can't add quick reservation to another instructor's fishing trip!");
        }
    }

    private void throwExceptionIfFishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeople(FishingTripQuickReservation fishingTripQuickReservation, FishingTrip fishingTrip) throws FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException {
        if (fishingTripQuickReservation.getMaxPeople() > fishingTrip.getMaxPeople()) {
            throw new FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException("Fishing trip quick reservation max people can't be higher than fishing trip max people!");
        }
    }

    private void throwExceptionIfQuickReservationStartDateInPast(FishingTripQuickReservation fishingTripQuickReservation) throws ReservationStartDateInPastException {
        if (fishingTripQuickReservation.getStart().isBefore(LocalDate.now())) {
            throw new ReservationStartDateInPastException("Quick reservation's start date can't be in the past!");
        }
    }

    private void throwExceptionIfValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDate(FishingTripQuickReservation fishingTripQuickReservation) throws ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException {
        if (fishingTripQuickReservation.getValidUntilAndIncluding().isBefore(LocalDate.now()) || fishingTripQuickReservation.getValidUntilAndIncluding().isAfter(fishingTripQuickReservation.getStart()) || fishingTripQuickReservation.getValidUntilAndIncluding().isEqual(fishingTripQuickReservation.getStart())) {
            throw new ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException("Quick reservation's valid until and including date can't be in the past or, after or equal to start date!");
        }
    }

    private void throwExceptionIfFishingTripReservationTagsDontContainQuickReservationTag(FishingTripQuickReservation fishingTripQuickReservation, FishingTrip fishingTrip) throws FishingTripReservationTagsDontContainReservationTagException {
        if (fishingTripQuickReservation.getFishingTripReservationTags() != null) {
            for (FishingTripReservationTag fishingTripReservationTag : fishingTripQuickReservation.getFishingTripReservationTags()) {
                if (!fishingTrip.getFishingTripReservationTags().contains(fishingTripReservationTag)) {
                    throw new FishingTripReservationTagsDontContainReservationTagException("Fishing trip reservation tags must contain all quick reservation tags!");
                }
            }
        }
    }

    private void throwExceptionIfNoAvailablePeriodForQuickReservation(FishingInstructor loggedInInstructor, FishingTripQuickReservation fishingTripQuickReservation) throws NoAvailablePeriodForReservationException {
        boolean canReserve = false;
        for (FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod : _fishingInstructorAvailablePeriodRepository.findByFishingInstructor(loggedInInstructor.getId())) {
            if ((fishingTripQuickReservation.getStart().isAfter(fishingInstructorAvailablePeriod.getAvailableFrom()) || fishingTripQuickReservation.getStart().isEqual(fishingInstructorAvailablePeriod.getAvailableFrom())) && (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isBefore(fishingInstructorAvailablePeriod.getAvailableTo()) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fishingInstructorAvailablePeriod.getAvailableTo()))) {
                canReserve = true;
                break;
            }
        }

        if (!canReserve) {
            throw new NoAvailablePeriodForReservationException("You don't have available period to reserve this quick reservation!");
        }
    }

    private void throwExceptionIfInstructorBusyDuringReservation(FishingInstructor loggedInInstructor, FishingTripQuickReservation fishingTripQuickReservation) throws InstructorBusyDuringReservationException {
        List<Integer> instructorFishingTripIds = _fishingTripRepository.findByFishingInstructor(loggedInInstructor.getId());
        List<FishingTripQuickReservation> instructorQuickReservations =  _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        for (FishingTripQuickReservation fiqr : instructorQuickReservations) {
            if (((fiqr.getStart().isBefore(fishingTripQuickReservation.getStart()) || fiqr.getStart().isEqual(fishingTripQuickReservation.getStart())) && (fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1).isAfter(fishingTripQuickReservation.getStart()) || fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1).isEqual(fishingTripQuickReservation.getStart()))) || ((fishingTripQuickReservation.getStart().isBefore(fiqr.getStart()) || fishingTripQuickReservation.getStart().isEqual(fiqr.getStart())) && (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isAfter(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)))) || ((fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isAfter(fiqr.getStart()) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fiqr.getStart())) && (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isBefore(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1))))){
                throw new InstructorBusyDuringReservationException("Instructor busy during reservation!");
            }
        }

        List<FishingTripReservation> instructorReservations = _fishingTripReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        for (FishingTripReservation fir : instructorReservations) {
            if (((fir.getStart().isBefore(fishingTripQuickReservation.getStart()) || fir.getStart().isEqual(fishingTripQuickReservation.getStart())) && (fir.getStart().plusDays(fir.getDurationInDays() - 1).isAfter(fishingTripQuickReservation.getStart()) || fir.getStart().plusDays(fir.getDurationInDays() - 1).isEqual(fishingTripQuickReservation.getStart()))) || ((fishingTripQuickReservation.getStart().isBefore(fir.getStart()) || fishingTripQuickReservation.getStart().isEqual(fir.getStart())) && (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isAfter(fir.getStart().plusDays(fir.getDurationInDays() - 1)) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fir.getStart().plusDays(fir.getDurationInDays() - 1)))) || ((fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isAfter(fir.getStart()) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fir.getStart())) && (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isBefore(fir.getStart().plusDays(fir.getDurationInDays() - 1)) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fir.getStart().plusDays(fir.getDurationInDays() - 1))))) {
                throw new InstructorBusyDuringReservationException("Instructor busy during reservation!");
            }
        }
    }

    private void sendQuickReservationCreatedMailToSubscribers(FishingInstructor loggedInInstructor) {
        for (Client subscriber : loggedInInstructor.getSubscribers()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("marko76589@gmail.com");
            message.setTo(subscriber.getEmail());
            message.setSubject("Action created");
            message.setText("Instructor " + loggedInInstructor.getFirstName() + " " + loggedInInstructor.getLastName() + " created a new action!");
            _mailSender.send(message);
        }
    }

    @Transactional
    @Override
    public void addReservation(Integer fishingTripId, Integer clientId, FishingTripReservation fishingTripReservation) throws FishingTripNotFoundException, AddReservationToAnotherInstructorFishingTripException, EnabledClientDoesntExistException, ReservationStartDateInPastException, FishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeopleException, FishingTripReservationTagsDontContainReservationTagException, NoAvailablePeriodForReservationException, ClientBannedException, ClientBusyDuringReservationException, InstructorBusyDuringReservationException {
        FishingInstructor loggedInInstructor = getLoggedInFishingInstructor();
        throwExceptionIfFishingTripNotFound(fishingTripId);
        FishingTrip fishingTrip = _fishingTripRepository.getById(fishingTripId);
        throwExceptionIfAddReservationToAnotherInstructorFishingTrip(loggedInInstructor, fishingTrip);
        _userService.throwExceptionIfEnabledClientDoesntExist(clientId);
        throwExceptionIfFishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeople(fishingTripReservation, fishingTrip);
        throwExceptionIfReservationStartDateInPast(fishingTripReservation);
        throwExceptionIfFishingTripReservationTagsDontContainQuickReservationTag(fishingTripReservation, fishingTrip);
        throwExceptionIfNoAvailablePeriodForReservation(loggedInInstructor, fishingTripReservation);
        throwExceptionIfInstructorBusyDuringReservation(loggedInInstructor, fishingTripReservation);
        throwExceptionIfClientBanned(clientId);
        throwExceptionIfClientBusyDuringReservation(clientId, fishingTripReservation);
        fishingTripReservation.setFishingTrip(fishingTrip);
        fishingTripReservation.setClient((Client) _userService.findById(clientId));
        fishingTripReservation.setPrice(fishingTrip.getCostPerDay() * fishingTripReservation.getDurationInDays() * (100 - fishingTripReservation.getClient().getCategory().getDiscountPercentage()) / 100);
        fishingTripReservation.setSystemTaxPercentage(_currentSystemTaxPercentageRepository.getById(1).getCurrentSystemTaxPercentage() - loggedInInstructor.getCategory().getLesserSystemTaxPercentage());
        fishingTripReservation.setLoyaltyPointsGiven(false);
        _fishingTripReservationRepository.save(fishingTripReservation);
        sendReservationCreatedMailToClient(clientId, loggedInInstructor);
        createIncomeRecord(fishingTripReservation, fishingTrip, loggedInInstructor);
    }

    private void throwExceptionIfAddReservationToAnotherInstructorFishingTrip(FishingInstructor loggedInInstructor, FishingTrip fishingTrip) throws AddReservationToAnotherInstructorFishingTripException {
        if (fishingTrip.getFishingInstructor().getId() != loggedInInstructor.getId()) {
            throw new AddReservationToAnotherInstructorFishingTripException("You can't add reservation to another instructor's fishing trip!");
        }
    }

    private void throwExceptionIfFishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeople(FishingTripReservation fishingTripReservation, FishingTrip fishingTrip) throws FishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeopleException {
        if (fishingTripReservation.getNumberOfPeople() > fishingTrip.getMaxPeople()) {
            throw new FishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeopleException("Fishing trip reservation number of people can't be higher than fishing trip max people!");
        }
    }

    private void throwExceptionIfReservationStartDateInPast(FishingTripReservation fishingTripReservation) throws ReservationStartDateInPastException {
        if (fishingTripReservation.getStart().isBefore(LocalDate.now())) {
            throw new ReservationStartDateInPastException("Reservation's start date can't be in the past!");
        }
    }

    private void throwExceptionIfFishingTripReservationTagsDontContainQuickReservationTag(FishingTripReservation fishingTripReservation, FishingTrip fishingTrip) throws FishingTripReservationTagsDontContainReservationTagException {
        if (fishingTripReservation.getFishingTripReservationTags() != null) {
            for (FishingTripReservationTag fishingTripReservationTag : fishingTripReservation.getFishingTripReservationTags()) {
                if (!fishingTrip.getFishingTripReservationTags().contains(fishingTripReservationTag)) {
                    throw new FishingTripReservationTagsDontContainReservationTagException("Fishing trip reservation tags must contain all reservation tags!");
                }
            }
        }
    }

    private void throwExceptionIfNoAvailablePeriodForReservation(FishingInstructor loggedInInstructor, FishingTripReservation fishingTripReservation) throws NoAvailablePeriodForReservationException {
        boolean canReserve = false;
        for (FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod : _fishingInstructorAvailablePeriodRepository.findByFishingInstructor(loggedInInstructor.getId())) {
            if ((fishingTripReservation.getStart().isAfter(fishingInstructorAvailablePeriod.getAvailableFrom()) || fishingTripReservation.getStart().isEqual(fishingInstructorAvailablePeriod.getAvailableFrom())) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isBefore(fishingInstructorAvailablePeriod.getAvailableTo()) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fishingInstructorAvailablePeriod.getAvailableTo()))) {
                canReserve = true;
                break;
            }
        }

        if (!canReserve) {
            throw new NoAvailablePeriodForReservationException("You don't have available period to reserve this reservation!");
        }
    }

    private void throwExceptionIfInstructorBusyDuringReservation(FishingInstructor loggedInInstructor, FishingTripReservation fishingTripReservation) throws InstructorBusyDuringReservationException {
        List<Integer> instructorFishingTripIds = _fishingTripRepository.findByFishingInstructor(loggedInInstructor.getId());
        List<FishingTripQuickReservation> instructorQuickReservations = _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        for (FishingTripQuickReservation fiqr : instructorQuickReservations) {
            if (((fiqr.getStart().isBefore(fishingTripReservation.getStart()) || fiqr.getStart().isEqual(fishingTripReservation.getStart())) && (fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1).isAfter(fishingTripReservation.getStart()) || fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1).isEqual(fishingTripReservation.getStart()))) || ((fishingTripReservation.getStart().isBefore(fiqr.getStart()) || fishingTripReservation.getStart().isEqual(fiqr.getStart())) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)))) || ((fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(fiqr.getStart()) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fiqr.getStart())) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isBefore(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1))))) {
                throw new InstructorBusyDuringReservationException("Instructor busy during reservation!");
            }
        }

        List<FishingTripReservation> instructorReservations = _fishingTripReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        for (FishingTripReservation fir : instructorReservations) {
            if (((fir.getStart().isBefore(fishingTripReservation.getStart()) || fir.getStart().isEqual(fishingTripReservation.getStart())) && (fir.getStart().plusDays(fir.getDurationInDays() - 1).isAfter(fishingTripReservation.getStart()) || fir.getStart().plusDays(fir.getDurationInDays() - 1).isEqual(fishingTripReservation.getStart()))) || ((fishingTripReservation.getStart().isBefore(fir.getStart()) || fishingTripReservation.getStart().isEqual(fir.getStart())) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(fir.getStart().plusDays(fir.getDurationInDays() - 1)) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fir.getStart().plusDays(fir.getDurationInDays() - 1)))) || ((fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(fir.getStart()) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fir.getStart())) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isBefore(fir.getStart().plusDays(fir.getDurationInDays() - 1)) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fir.getStart().plusDays(fir.getDurationInDays() - 1))))) {
                throw new InstructorBusyDuringReservationException("Instructor busy during reservation!");
            }
        }
    }

    private void throwExceptionIfClientBanned(Integer clientId) throws ClientBannedException {
        Client client = (Client) _userService.findById(clientId);
        if (client.isBanned()) {
            throw new ClientBannedException("Client is banned!");
        }
    }

    private void throwExceptionIfClientBusyDuringReservation(Integer clientId, FishingTripReservation fishingTripReservation) throws ClientBusyDuringReservationException {
        List<FishingTripQuickReservation> clientFishingTripQuickReservations = _fishingTripQuickReservationRepository.findByClientId(clientId);
        for (FishingTripQuickReservation fiqr : clientFishingTripQuickReservations) {
            if (((fiqr.getStart().isBefore(fishingTripReservation.getStart()) || fiqr.getStart().isEqual(fishingTripReservation.getStart())) && (fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1).isAfter(fishingTripReservation.getStart()) || fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1).isEqual(fishingTripReservation.getStart()))) || ((fishingTripReservation.getStart().isBefore(fiqr.getStart()) || fishingTripReservation.getStart().isEqual(fiqr.getStart())) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)))) || ((fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(fiqr.getStart()) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fiqr.getStart())) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isBefore(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1))))){
                throw new ClientBusyDuringReservationException("Client busy during reservation!");
            }
        }

        List<FishingTripReservation> clientFishingTripReservations = _fishingTripReservationRepository.findByClientId(clientId);
        for (FishingTripReservation fir : clientFishingTripReservations) {
            if (((fir.getStart().isBefore(fishingTripReservation.getStart()) || fir.getStart().isEqual(fishingTripReservation.getStart())) && (fir.getStart().plusDays(fir.getDurationInDays() - 1).isAfter(fishingTripReservation.getStart()) || fir.getStart().plusDays(fir.getDurationInDays() - 1).isEqual(fishingTripReservation.getStart()))) || ((fishingTripReservation.getStart().isBefore(fir.getStart()) || fishingTripReservation.getStart().isEqual(fir.getStart())) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(fir.getStart().plusDays(fir.getDurationInDays() - 1)) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fir.getStart().plusDays(fir.getDurationInDays() - 1)))) || ((fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(fir.getStart()) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fir.getStart())) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isBefore(fir.getStart().plusDays(fir.getDurationInDays() - 1)) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(fir.getStart().plusDays(fir.getDurationInDays() - 1))))){
                throw new ClientBusyDuringReservationException("Client busy during reservation!");
            }
        }

        List<Reservation> clientReservations = _reservationRepository.findByClientId(clientId);
        for (Reservation r : clientReservations) {
            LocalDate reservationStart = r.getDateRange().getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate reservationEnd = r.getDateRange().getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (((reservationStart.isBefore(fishingTripReservation.getStart()) || reservationStart.isEqual(fishingTripReservation.getStart())) && (reservationEnd.isAfter(fishingTripReservation.getStart()) || reservationEnd.isEqual(fishingTripReservation.getStart()))) || ((fishingTripReservation.getStart().isBefore(reservationStart) || fishingTripReservation.getStart().isEqual(reservationStart)) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(reservationEnd) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(reservationEnd))) || ((fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(reservationStart) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(reservationStart)) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isBefore(reservationEnd) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(reservationEnd)))){
                throw new ClientBusyDuringReservationException("Client busy during reservation!");
            }
        }

        List<Action> clientActions = _actionRepository.findByClientId(clientId);
        for (Action a : clientActions) {
            LocalDate reservationStart = a.getRange().getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate reservationEnd = a.getRange().getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (((reservationStart.isBefore(fishingTripReservation.getStart()) || reservationStart.isEqual(fishingTripReservation.getStart())) && (reservationEnd.isAfter(fishingTripReservation.getStart()) || reservationEnd.isEqual(fishingTripReservation.getStart()))) || ((fishingTripReservation.getStart().isBefore(reservationStart) || fishingTripReservation.getStart().isEqual(reservationStart)) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(reservationEnd) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(reservationEnd))) || ((fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isAfter(reservationStart) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(reservationStart)) && (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isBefore(reservationEnd) || fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isEqual(reservationEnd)))){
                throw new ClientBusyDuringReservationException("Client busy during reservation!");
            }
        }
    }

    private void sendReservationCreatedMailToClient(Integer clientId, FishingInstructor loggedInInstructor) {
        Client client = (Client) _userService.findById(clientId);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(client.getEmail());
        message.setSubject("Reservation created");
        message.setText("Instructor " + loggedInInstructor.getFirstName() + " " + loggedInInstructor.getLastName() + " created reservation for you!");
        _mailSender.send(message);
    }

    private void createIncomeRecord(FishingTripReservation fishingTripReservation, FishingTrip fishingTrip, FishingInstructor loggedInInstructor) {
        IncomeRecord incomeRecord = new IncomeRecord();
        incomeRecord.setReserved(true);
        incomeRecord.setDateOfEntry(LocalDate.now());
        incomeRecord.setReservationProvider(loggedInInstructor);
        incomeRecord.setReservationStart(fishingTripReservation.getStart());
        incomeRecord.setReservationEnd(fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1));
        incomeRecord.setReservationPrice(fishingTripReservation.getPrice());
        incomeRecord.setSystemTaxPercentage(fishingTripReservation.getSystemTaxPercentage());
        incomeRecord.setPercentageProviderKeepsIfReservationCancelled(fishingTrip.getPercentageInstructorKeepsIfReservationCancelled());
        double systemIncome = incomeRecord.getReservationPrice() * incomeRecord.getSystemTaxPercentage() / 100;
        incomeRecord.setSystemIncome(systemIncome);
        incomeRecord.setProviderIncome(incomeRecord.getReservationPrice() - systemIncome);
        _incomeRecordRepository.save(incomeRecord);
    }

    @Transactional
    @Override
    public void addReservationCommentary(Integer reservationId, OwnerCommentary ownerCommentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionAlreadyCommented, ReservationOrActionNotFinishedException {
        throwExceptionIfReservationDoesntExist(reservationId);
        FishingTripReservation fishingTripReservation = _fishingTripReservationRepository.getById(reservationId);
        throwExceptionIfReservationDoesntBelongToLoggedInInstructor(fishingTripReservation);
        throwExceptionIfReservationNotFinished(fishingTripReservation);
        throwExceptionIfReservationAlreadyCommented(fishingTripReservation);

        ownerCommentary.setPenaltyGiven(!ownerCommentary.isClientCame());
        ownerCommentary.setAdminApproved(false);
        fishingTripReservation.setOwnerCommentary(ownerCommentary);

        if (!ownerCommentary.isClientCame()) {
            fishingTripReservation.getOwnerCommentary().setSanctionSuggested(false);
            fishingTripReservation.getClient().setPenaltyPoints(fishingTripReservation.getClient().getPenaltyPoints() + 1);
            if (fishingTripReservation.getClient().getPenaltyPoints() == 3) {
                fishingTripReservation.getClient().setBanned(true);
            }
            _clientRepository.save(fishingTripReservation.getClient());
        }

        _fishingTripReservationRepository.save(fishingTripReservation);
    }

    private void throwExceptionIfReservationDoesntExist(Integer reservationId) throws EntityNotFoundException {
        Optional<FishingTripReservation> fishingTripReservation = _fishingTripReservationRepository.findById(reservationId);
        if (!fishingTripReservation.isPresent()) {
            throw new EntityNotFoundException("Reservation doesn't exist!");
        }
    }

    private void throwExceptionIfReservationDoesntBelongToLoggedInInstructor(FishingTripReservation fishingTripReservation) throws EntityNotOwnedException {
        List<Integer> instructorFishingTripIds = _fishingTripRepository.findByFishingInstructor(getLoggedInFishingInstructor().getId());
        List<FishingTripReservation> instructorReservations = _fishingTripReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        if (!instructorReservations.contains(fishingTripReservation)) {
            throw new EntityNotOwnedException("Reservation doesn't belong to logged in instructor!");
        }
    }

    private void throwExceptionIfReservationNotFinished(FishingTripReservation fishingTripReservation) throws ReservationOrActionNotFinishedException {
        if (fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays()).isAfter(LocalDate.now())) {
            throw new ReservationOrActionNotFinishedException("Reservation not finished!");
        }
    }

    private void throwExceptionIfReservationAlreadyCommented(FishingTripReservation fishingTripReservation) throws ReservationOrActionAlreadyCommented {
        if (fishingTripReservation.getOwnerCommentary() != null) {
            throw new ReservationOrActionAlreadyCommented("Reservation already commented!");
        }
    }

    @Transactional
    @Override
    public void addQuickReservationCommentary(Integer quickReservationId, OwnerCommentary ownerCommentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented {
        throwExceptionIfQuickReservationDoesntExist(quickReservationId);
        FishingTripQuickReservation fishingTripQuickReservation = _fishingTripQuickReservationRepository.getById(quickReservationId);
        throwExceptionIfQuickReservationDoesntBelongToLoggedInInstructor(fishingTripQuickReservation);
        throwExceptionIfQuickReservationNotFinished(fishingTripQuickReservation);
        throwExceptionIfQuickReservationAlreadyCommented(fishingTripQuickReservation);

        ownerCommentary.setPenaltyGiven(!ownerCommentary.isClientCame());
        ownerCommentary.setAdminApproved(false);
        fishingTripQuickReservation.setOwnerCommentary(ownerCommentary);

        if (!ownerCommentary.isClientCame()) {
            fishingTripQuickReservation.getOwnerCommentary().setSanctionSuggested(false);
            fishingTripQuickReservation.getClient().setPenaltyPoints(fishingTripQuickReservation.getClient().getPenaltyPoints() + 1);
            if (fishingTripQuickReservation.getClient().getPenaltyPoints() == 3) {
                fishingTripQuickReservation.getClient().setBanned(true);
            }
            _clientRepository.save(fishingTripQuickReservation.getClient());
        }

        _fishingTripQuickReservationRepository.save(fishingTripQuickReservation);
    }

    private void throwExceptionIfQuickReservationDoesntExist(Integer quickReservationId) throws EntityNotFoundException {
        Optional<FishingTripQuickReservation> fishingTripQuickReservation = _fishingTripQuickReservationRepository.findById(quickReservationId);
        if (!fishingTripQuickReservation.isPresent()) {
            throw new EntityNotFoundException("Quick reservation doesn't exist!");
        }
    }

    private void throwExceptionIfQuickReservationDoesntBelongToLoggedInInstructor(FishingTripQuickReservation fishingTripQuickReservation) throws EntityNotOwnedException {
        List<Integer> instructorFishingTripIds = _fishingTripRepository.findByFishingInstructor(getLoggedInFishingInstructor().getId());
        List<FishingTripQuickReservation> instructorQuickReservations = _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        if (!instructorQuickReservations.contains(fishingTripQuickReservation)) {
            throw new EntityNotOwnedException("Quick reservation doesn't belong to logged in instructor!");
        }
    }

    private void throwExceptionIfQuickReservationNotFinished(FishingTripQuickReservation fishingTripQuickReservation) throws ReservationOrActionNotFinishedException {
        if (fishingTripQuickReservation.getClient() == null || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays()).isAfter(LocalDate.now())) {
            throw new ReservationOrActionNotFinishedException("Quick reservation not finished or doesn't have a client!");
        }
    }

    private void throwExceptionIfQuickReservationAlreadyCommented(FishingTripQuickReservation fishingTripQuickReservation) throws ReservationOrActionAlreadyCommented {
        if (fishingTripQuickReservation.getOwnerCommentary() != null) {
            throw new ReservationOrActionAlreadyCommented("Quick reservation already commented!");
        }
    }

    @Override
    public List<FishingInstructorFishingTripTableGetDto> getFishingInstructorFishingTrips() {
        List<FishingTrip> instructorFishingTrips = _fishingTripRepository.findByFishingInstructorId(getLoggedInFishingInstructor().getId());
        List<FishingInstructorFishingTripTableGetDto> fishingInstructorFishingTripTableGetDtos = new ArrayList<>();
        for (FishingTrip fishingTrip : instructorFishingTrips) {
            fishingInstructorFishingTripTableGetDtos.add(new FishingInstructorFishingTripTableGetDto(fishingTrip.getId(), fishingTrip.getName(), fishingTrip.getMaxPeople(), fishingTrip.getCostPerDay(), fishingTrip.getLocation().getStreetName(), fishingTrip.getLocation().getCityName(), fishingTrip.getLocation().getCountryName()));
        }

        return fishingInstructorFishingTripTableGetDtos;
    }

    @Override
    public List<FishingInstructorFishingTripTableGetDto> getSearchedFishingTrips(String searchText) {
        List<FishingTrip> instructorFishingTrips = _fishingTripRepository.findByFishingInstructorId(getLoggedInFishingInstructor().getId());
        List<FishingInstructorFishingTripTableGetDto> searchedInstructorFishingTrips = new ArrayList<>();
        for (FishingTrip fishingTrip : instructorFishingTrips) {
            if (fishingTrip.getName().toUpperCase().startsWith(searchText.toUpperCase())) {
                searchedInstructorFishingTrips.add(new FishingInstructorFishingTripTableGetDto(fishingTrip.getId(), fishingTrip.getName(), fishingTrip.getMaxPeople(), fishingTrip.getCostPerDay(), fishingTrip.getLocation().getStreetName(), fishingTrip.getLocation().getCityName(), fishingTrip.getLocation().getCountryName()));
            }
        }

        return searchedInstructorFishingTrips;
    }

    @Override
    public List<FishingInstructorReservationTableGetDto> getFishingInstructorReservations() {
        List<Integer> instructorFishingTripIds = _fishingTripRepository.findByFishingInstructor(getLoggedInFishingInstructor().getId());
        List<FishingTripReservation> instructorFishingTripReservations = _fishingTripReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        List<FishingTripQuickReservation> instructorFishingTripQuickReservations = _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        List<FishingInstructorReservationTableGetDto> fishingInstructorReservationTableGetDtos = new ArrayList<>();

        for (FishingTripReservation fishingTripReservation : instructorFishingTripReservations) {
            fishingInstructorReservationTableGetDtos.add(new FishingInstructorReservationTableGetDto(fishingTripReservation.getStart(), fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1), fishingTripReservation.getNumberOfPeople(), fishingTripReservation.getPrice(), fishingTripReservation.getSystemTaxPercentage(), Lists.newArrayList(fishingTripReservation.getFishingTripReservationTags()), fishingTripReservation.getFishingTrip().getName(), fishingTripReservation.getClient().getId(), fishingTripReservation.getClient().getFirstName(), fishingTripReservation.getClient().getLastName()));
        }

        for (FishingTripQuickReservation fishingTripQuickReservation : instructorFishingTripQuickReservations) {
            if (fishingTripQuickReservation.getClient() != null) {
                fishingInstructorReservationTableGetDtos.add(new FishingInstructorReservationTableGetDto(fishingTripQuickReservation.getStart(), fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1), fishingTripQuickReservation.getMaxPeople(), fishingTripQuickReservation.getPrice(), fishingTripQuickReservation.getSystemTaxPercentage(), Lists.newArrayList(fishingTripQuickReservation.getFishingTripReservationTags()), fishingTripQuickReservation.getFishingTrip().getName(), fishingTripQuickReservation.getClient().getId(), fishingTripQuickReservation.getClient().getFirstName(), fishingTripQuickReservation.getClient().getLastName()));
            }
        }

        Collections.sort(fishingInstructorReservationTableGetDtos, Comparator.comparing(FishingInstructorReservationTableGetDto::getStart));
        return fishingInstructorReservationTableGetDtos;
    }

    @Scheduled(cron="0 0 1 * * *")
    @Transactional
    public void deleteNonValidQuickReservations() {
        List<FishingTrip> allFishingTrips = _fishingTripRepository.findAll();
        List<FishingTripQuickReservation> allFishingTripQuickReservations =  _fishingTripQuickReservationRepository.findAll();

        for (FishingTrip fishingTrip : allFishingTrips) {
            for (FishingTripQuickReservation fishingTripQuickReservation : allFishingTripQuickReservations) {
                if (fishingTripQuickReservation.getFishingTrip() != null && fishingTrip.getId() == fishingTripQuickReservation.getFishingTrip().getId() && fishingTripQuickReservation.getClient() == null && fishingTripQuickReservation.getValidUntilAndIncluding().isBefore(LocalDate.now())) {
                    fishingTrip.getFishingTripQuickReservations().remove(fishingTripQuickReservation);
                    fishingTripQuickReservation.setFishingTrip(null);
                    _fishingTripQuickReservationRepository.delete(fishingTripQuickReservation);
                }
            }
        }
    }

    @Scheduled(cron="0 0 1 * * *")
    @Transactional
    public void awardLoyaltyPoints() {
        List<FishingTripReservation> allFishingTripReservations =  _fishingTripReservationRepository.findAll();
        List<FishingTripQuickReservation> allFishingTripQuickReservations =  _fishingTripQuickReservationRepository.findAll();

        for (FishingTripReservation fishingTripReservation : allFishingTripReservations) {
            if (!fishingTripReservation.isLoyaltyPointsGiven() && fishingTripReservation.getStart().plusDays(fishingTripReservation.getDurationInDays() - 1).isBefore(LocalDate.now())) {
                fishingTripReservation.getClient().setLoyaltyPoints(fishingTripReservation.getClient().getLoyaltyPoints() + _currentPointsClientGetsAfterReservationRepository.getById(1).getCurrentPointsClientGetsAfterReservation());
                List<LoyaltyProgramClient> loyaltyProgramClients = _loyaltyProgramClientRepository.findAll();
                Collections.sort(loyaltyProgramClients, Comparator.comparing(LoyaltyProgramClient::getPointsNeeded));
                for (LoyaltyProgramClient lpc : loyaltyProgramClients) {
                    if (fishingTripReservation.getClient().getLoyaltyPoints() >= lpc.getPointsNeeded()) {
                        fishingTripReservation.getClient().setCategory(lpc);
                    }
                }
                _clientRepository.save(fishingTripReservation.getClient());

                fishingTripReservation.getFishingTrip().getFishingInstructor().setLoyaltyPoints(fishingTripReservation.getFishingTrip().getFishingInstructor().getLoyaltyPoints() + _currentPointsProviderGetsAfterReservationRepository.getById(1).getCurrentPointsProviderGetsAfterReservation());
                List<LoyaltyProgramProvider> loyaltyProgramProviders = _loyaltyProgramProviderRepository.findAll();
                Collections.sort(loyaltyProgramProviders, Comparator.comparing(LoyaltyProgramProvider::getPointsNeeded));
                for (LoyaltyProgramProvider lpp : loyaltyProgramProviders) {
                    if (fishingTripReservation.getFishingTrip().getFishingInstructor().getLoyaltyPoints() >= lpp.getPointsNeeded()) {
                        fishingTripReservation.getFishingTrip().getFishingInstructor().setCategory(lpp);
                    }
                }
                _fishingInstructorRepository.save(fishingTripReservation.getFishingTrip().getFishingInstructor());

                fishingTripReservation.setLoyaltyPointsGiven(true);
                _fishingTripReservationRepository.save(fishingTripReservation);
            }
        }

        for (FishingTripQuickReservation fishingTripQuickReservation : allFishingTripQuickReservations) {
            if (!fishingTripQuickReservation.isLoyaltyPointsGiven() && fishingTripQuickReservation.getClient() != null && fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isBefore(LocalDate.now())) {
                fishingTripQuickReservation.getClient().setLoyaltyPoints(fishingTripQuickReservation.getClient().getLoyaltyPoints() + _currentPointsClientGetsAfterReservationRepository.getById(1).getCurrentPointsClientGetsAfterReservation());
                List<LoyaltyProgramClient> loyaltyProgramClients = _loyaltyProgramClientRepository.findAll();
                Collections.sort(loyaltyProgramClients, Comparator.comparing(LoyaltyProgramClient::getPointsNeeded));
                for (LoyaltyProgramClient lpc : loyaltyProgramClients) {
                    if (fishingTripQuickReservation.getClient().getLoyaltyPoints() >= lpc.getPointsNeeded()) {
                        fishingTripQuickReservation.getClient().setCategory(lpc);
                    }
                }
                _clientRepository.save(fishingTripQuickReservation.getClient());

                fishingTripQuickReservation.getFishingTrip().getFishingInstructor().setLoyaltyPoints(fishingTripQuickReservation.getFishingTrip().getFishingInstructor().getLoyaltyPoints() + _currentPointsProviderGetsAfterReservationRepository.getById(1).getCurrentPointsProviderGetsAfterReservation());
                List<LoyaltyProgramProvider> loyaltyProgramProviders = _loyaltyProgramProviderRepository.findAll();
                Collections.sort(loyaltyProgramProviders, Comparator.comparing(LoyaltyProgramProvider::getPointsNeeded));
                for (LoyaltyProgramProvider lpp : loyaltyProgramProviders) {
                    if (fishingTripQuickReservation.getFishingTrip().getFishingInstructor().getLoyaltyPoints() >= lpp.getPointsNeeded()) {
                        fishingTripQuickReservation.getFishingTrip().getFishingInstructor().setCategory(lpp);
                    }
                }
                _fishingInstructorRepository.save(fishingTripQuickReservation.getFishingTrip().getFishingInstructor());

                fishingTripQuickReservation.setLoyaltyPointsGiven(true);
                _fishingTripQuickReservationRepository.save(fishingTripQuickReservation);
            }
        }
    }

    @Override
    public List<PictureBase64> getFishingTripImagesAsBase64(int id) throws EntityNotFoundException, IOException {
        FishingTrip fishingTrip = _fishingTripRepository.findById(id).orElse(null);
        if(fishingTrip == null) {
            throw new EntityNotFoundException("Fishing trip doesn't exist!");
        }
        List<PictureBase64> pictures = new ArrayList<>();
        for(FishingTripPicture pic: fishingTrip.getPictures()){
            pictures.add(new PictureBase64(FileUploadUtil.convertToBase64(FISHING_TRIP_PICTURE_DIRECTORY, fishingTrip.getId() + "_" + pic.getName()), pic.getId()));
        }
        return pictures;
    }
}
