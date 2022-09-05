package code.service;

import code.dto.fishing_trip.FishingQuickReservationGetDto;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.EntityNotOwnedException;
import code.exceptions.entities.ReservationOrActionAlreadyCommented;
import code.exceptions.entities.ReservationOrActionNotFinishedException;
import code.exceptions.fishing_trip.*;
import code.exceptions.fishing_trip.quick_reservation.*;
import code.exceptions.fishing_trip.reservation.*;
import code.model.FishingTrip;
import code.model.FishingTripQuickReservation;
import code.model.FishingTripReservation;
import code.model.ReviewFishingTrip;
import code.model.base.OwnerCommentary;
import code.model.base.PictureBase64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface FishingTripService {
    FishingTrip save(FishingTrip fishingTrip);
    List<FishingTrip> getAllFishingTrips();
    FishingTrip getFishingTrip(Integer id) throws EntityNotFoundException;
    FishingTrip edit(FishingTrip fishingTrip) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, FishingTripHasQuickReservationWithClientException, FishingTripHasReservationException;
    void editPictures(Integer id, MultipartFile[] pictures) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, IOException, FishingTripHasQuickReservationWithClientException, FishingTripHasReservationException;
    void addQuickReservation(Integer id, FishingTripQuickReservation fishingTripQuickReservation) throws AddReservationToAnotherInstructorFishingTripException, FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException, ReservationStartDateInPastException, ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException, FishingTripReservationTagsDontContainReservationTagException, NoAvailablePeriodForReservationException, FishingTripNotFoundException, InstructorBusyDuringReservationException;
    void deleteFishingTrip(Integer id) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, FishingTripHasQuickReservationWithClientException, FishingTripHasReservationException;
    void addReservation(Integer fishingTripId, Integer clientId, FishingTripReservation fishingTripReservation) throws FishingTripNotFoundException, AddReservationToAnotherInstructorFishingTripException, EnabledClientDoesntExistException, ReservationStartDateInPastException, FishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeopleException, FishingTripReservationTagsDontContainReservationTagException, NoAvailablePeriodForReservationException, ClientBannedException, ClientBusyDuringReservationException, InstructorBusyDuringReservationException;
    void addReservationCommentary(Integer reservationId, OwnerCommentary ownerCommentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionAlreadyCommented, ReservationOrActionNotFinishedException;
    void addQuickReservationCommentary(Integer quickReservationId, OwnerCommentary ownerCommentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented;
    List<FishingTrip> getFishingInstructorFishingTrips();
    List<FishingTrip> getSearchedFishingTrips(String searchText);
    List<FishingTripReservation> getFishingInstructorReservations();
    List<PictureBase64> getFishingTripImagesAsBase64(int id) throws EntityNotFoundException, IOException;
    List<FishingTripQuickReservation> getFishingInstructorQuickReservations();
    List<FishingTripQuickReservation> getFishingTripFreeQuickReservations(Integer id) throws EntityNotFoundException;
    List<ReviewFishingTrip> getFishingTripApprovedReviews(Integer id) throws EntityNotFoundException;
}