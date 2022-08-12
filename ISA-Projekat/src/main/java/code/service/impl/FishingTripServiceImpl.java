package code.service.impl;

import code.exceptions.fishing_instructor.AvailablePeriodOverlappingException;
import code.exceptions.fishing_trip.*;
import code.exceptions.fishing_trip.quick_reservation.*;
import code.model.*;
import code.repository.FishingInstructorAvailablePeriodRepository;
import code.repository.FishingTripPictureRepository;
import code.repository.FishingTripQuickReservationRepository;
import code.repository.FishingTripRepository;
import code.service.FishingTripService;
import code.service.UserService;
import code.utils.FileUploadUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class FishingTripServiceImpl implements FishingTripService {
    private final FishingTripRepository _fishingTripRepository;
    private final FishingTripPictureRepository _fishingTripPictureRepository;
    private final FishingInstructorAvailablePeriodRepository _fishingInstructorAvailablePeriodRepository;
    private final FishingTripQuickReservationRepository _fishingTripQuickReservationRepository;
    private final UserService _userService;

    public FishingTripServiceImpl(FishingTripPictureRepository fishingTripPictureRepository, FishingTripRepository fishingTripRepository, FishingInstructorAvailablePeriodRepository fishingInstructorAvailablePeriodRepository, FishingTripQuickReservationRepository fishingTripQuickReservationRepository, UserService userService) {
        this._fishingTripPictureRepository = fishingTripPictureRepository;
        this._fishingTripRepository = fishingTripRepository;
        this._fishingInstructorAvailablePeriodRepository = fishingInstructorAvailablePeriodRepository;
        this._fishingTripQuickReservationRepository = fishingTripQuickReservationRepository;
        this._userService = userService;
    }

    @Override
    public FishingTrip save(FishingTrip fishingTrip) {
        setInstructor(fishingTrip);
        return _fishingTripRepository.save(fishingTrip);
    }

    @Transactional
    @Override
    public FishingTrip edit(FishingTrip fishingTrip) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException {
        throwExceptionIfFishingTripNotFound(fishingTrip.getId());
        throwExceptionIfEditAnotherInstructorFishingTrip(fishingTrip.getId());
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
    public void editPictures(Integer id, MultipartFile[] pictures) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, IOException {
        throwExceptionIfFishingTripNotFound(id);
        throwExceptionIfEditAnotherInstructorFishingTrip(id);

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
    public void addQuickReservation(Integer id, FishingTripQuickReservation fishingTripQuickReservation) throws AddQuickReservationToAnotherInstructorFishingTripException, FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException, QuickReservationStartDateInPastException, ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException, FishingTripReservationTagsDontContainQuickReservationTagException, NoAvailablePeriodForQuickReservationException, QuickReservationOverlappingException, FishingTripNotFoundException {
        FishingInstructor loggedInInstructor = getLoggedInFishingInstructor();
        throwExceptionIfFishingTripNotFound(id);
        FishingTrip fishingTrip = _fishingTripRepository.getById(id);
        throwExceptionIfAddQuickReservationToAnotherInstructorFishingTrip(loggedInInstructor, fishingTrip);
        throwExceptionIfFishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeople(fishingTripQuickReservation, fishingTrip);
        throwExceptionIfQuickReservationStartDateInPast(fishingTripQuickReservation);
        throwExceptionIfValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDate(fishingTripQuickReservation);
        throwExceptionIfFishingTripReservationTagsDontContainQuickReservationTag(fishingTripQuickReservation, fishingTrip);
        throwExceptionIfNoAvailablePeriodForQuickReservation(loggedInInstructor, fishingTripQuickReservation);
        deleteNonValidQuickReservations(fishingTrip);
        throwExceptionIfQuickReservationOverlapping(loggedInInstructor, fishingTripQuickReservation);
        fishingTripQuickReservation.setFishingTrip(fishingTrip);
        _fishingTripQuickReservationRepository.save(fishingTripQuickReservation);
    }

    private FishingInstructor getLoggedInFishingInstructor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return (FishingInstructor) _userService.findById(user.getId());
    }

    private void throwExceptionIfAddQuickReservationToAnotherInstructorFishingTrip(FishingInstructor loggedInInstructor, FishingTrip fishingTrip) throws AddQuickReservationToAnotherInstructorFishingTripException {
        if (fishingTrip.getFishingInstructor().getId() != loggedInInstructor.getId()) {
            throw new AddQuickReservationToAnotherInstructorFishingTripException("You can't add quick reservation to another instructor's fishing trip!");
        }
    }

    private void throwExceptionIfFishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeople(FishingTripQuickReservation fishingTripQuickReservation, FishingTrip fishingTrip) throws FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException {
        if (fishingTripQuickReservation.getMaxPeople() > fishingTrip.getMaxPeople()) {
            throw new FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException("Fishing trip quick reservation max people can't be higher than fishing trip max people!");
        }
    }

    private void throwExceptionIfQuickReservationStartDateInPast(FishingTripQuickReservation fishingTripQuickReservation) throws QuickReservationStartDateInPastException {
        if (fishingTripQuickReservation.getStart().isBefore(LocalDate.now())) {
            throw new QuickReservationStartDateInPastException("Quick reservation's start date can't be in the past!");
        }
    }

    private void throwExceptionIfValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDate(FishingTripQuickReservation fishingTripQuickReservation) throws ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException {
        if (fishingTripQuickReservation.getValidUntilAndIncluding().isBefore(LocalDate.now()) || fishingTripQuickReservation.getValidUntilAndIncluding().isAfter(fishingTripQuickReservation.getStart()) || fishingTripQuickReservation.getValidUntilAndIncluding().isEqual(fishingTripQuickReservation.getStart())) {
            throw new ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException("Quick reservation's valid until and including date can't be in the past or, after or equal to start date!");
        }
    }

    private void throwExceptionIfFishingTripReservationTagsDontContainQuickReservationTag(FishingTripQuickReservation fishingTripQuickReservation, FishingTrip fishingTrip) throws FishingTripReservationTagsDontContainQuickReservationTagException {
        if (fishingTripQuickReservation.getFishingTripReservationTags() != null) {
            for (FishingTripReservationTag fishingTripReservationTag : fishingTripQuickReservation.getFishingTripReservationTags()) {
                if (!fishingTrip.getFishingTripReservationTags().contains(fishingTripReservationTag)) {
                    throw new FishingTripReservationTagsDontContainQuickReservationTagException("Fishing trip reservation tags must contain all quick reservation tags!");
                }
            }
        }
    }

    private void throwExceptionIfNoAvailablePeriodForQuickReservation(FishingInstructor loggedInInstructor, FishingTripQuickReservation fishingTripQuickReservation) throws NoAvailablePeriodForQuickReservationException {
        boolean canReserve = false;
        for (FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod : _fishingInstructorAvailablePeriodRepository.findByFishingInstructor(loggedInInstructor.getId())) {
            if ((fishingTripQuickReservation.getStart().isAfter(fishingInstructorAvailablePeriod.getAvailableFrom()) || fishingTripQuickReservation.getStart().isEqual(fishingInstructorAvailablePeriod.getAvailableFrom())) && (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isBefore(fishingInstructorAvailablePeriod.getAvailableTo()) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fishingInstructorAvailablePeriod.getAvailableTo()))) {
                canReserve = true;
                break;
            }
        }

        if (!canReserve) {
            throw new NoAvailablePeriodForQuickReservationException("You don't have available period to reserve this quick reservation!");
        }
    }

    @Override
    public void deleteNonValidQuickReservations(FishingTrip fishingTrip) {
        List<FishingTripQuickReservation> fishingTripQuickReservations = _fishingTripQuickReservationRepository.findAll();
        for (FishingTripQuickReservation fishingTripQuickReservation : fishingTripQuickReservations) {
            if (fishingTripQuickReservation.getClient() == null && fishingTripQuickReservation.getValidUntilAndIncluding().isBefore(LocalDate.now())) {
                fishingTrip.getFishingTripQuickReservations().remove(fishingTripQuickReservation);
                fishingTripQuickReservation.setFishingTrip(null);
                _fishingTripQuickReservationRepository.delete(fishingTripQuickReservation);
            }
        }
    }

    private void throwExceptionIfQuickReservationOverlapping(FishingInstructor loggedInInstructor, FishingTripQuickReservation fishingTripQuickReservation) throws QuickReservationOverlappingException {
        List<Integer> instructorFishingTripIds = _fishingTripRepository.findByFishingInstructor(loggedInInstructor.getId());
        List<FishingTripQuickReservation> instructorQuickReservations =  _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripIds);
        for (FishingTripQuickReservation fiqr : instructorQuickReservations) {
            if (((fiqr.getStart().isBefore(fishingTripQuickReservation.getStart()) || fiqr.getStart().isEqual(fishingTripQuickReservation.getStart())) && (fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1).isAfter(fishingTripQuickReservation.getStart()) || fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1).isEqual(fishingTripQuickReservation.getStart()))) || ((fishingTripQuickReservation.getStart().isBefore(fiqr.getStart()) || fishingTripQuickReservation.getStart().isEqual(fiqr.getStart())) && (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isAfter(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)))) || ((fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isAfter(fiqr.getStart()) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fiqr.getStart())) && (fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isBefore(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1)) || fishingTripQuickReservation.getStart().plusDays(fishingTripQuickReservation.getDurationInDays() - 1).isEqual(fiqr.getStart().plusDays(fiqr.getDurationInDays() - 1))))){
                throw new QuickReservationOverlappingException("Quick reservation overlapping another!");
            }
        }
    }
}
