package code.service;

import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.EntityNotOwnedException;
import code.exceptions.entities.ReservationOrActionAlreadyCommented;
import code.exceptions.entities.ReservationOrActionNotFinishedException;
import code.exceptions.fishing_trip.*;
import code.exceptions.fishing_trip.quick_reservation.*;
import code.exceptions.fishing_trip.reservation.*;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.FishingInstructor;
import code.model.FishingTrip;
import code.model.FishingTripQuickReservation;
import code.model.FishingTripReservation;
import code.model.base.OwnerCommentary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FishingTripService {
    FishingTrip save(FishingTrip fishingTrip);
    FishingTrip edit(FishingTrip fishingTrip) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, FishingTripHasQuickReservationWithClientException, FishingTripHasReservationException;
    void editPictures(Integer id, MultipartFile[] pictures) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, IOException, FishingTripHasQuickReservationWithClientException, FishingTripHasReservationException;
    void addQuickReservation(Integer id, FishingTripQuickReservation fishingTripQuickReservation) throws AddReservationToAnotherInstructorFishingTripException, FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException, ReservationStartDateInPastException, ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException, FishingTripReservationTagsDontContainReservationTagException, NoAvailablePeriodForReservationException, FishingTripNotFoundException, InstructorBusyDuringReservationException;
    void deleteFishingTrip(Integer id) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, FishingTripHasQuickReservationWithClientException, FishingTripHasReservationException;
    void addReservation(Integer fishingTripId, Integer clientId, FishingTripReservation fishingTripReservation) throws FishingTripNotFoundException, AddReservationToAnotherInstructorFishingTripException, EnabledClientDoesntExistException, ReservationStartDateInPastException, FishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeopleException, FishingTripReservationTagsDontContainReservationTagException, NoAvailablePeriodForReservationException, ClientBannedException, ClientBusyDuringReservationException, InstructorBusyDuringReservationException;
    void addReservationCommentary(Integer reservationId, OwnerCommentary ownerCommentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionAlreadyCommented, ReservationOrActionNotFinishedException;
    void addQuickReservationCommentary(Integer quickReservationId, OwnerCommentary ownerCommentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented;
}