package code.service;

import code.exceptions.fishing_trip.*;
import code.exceptions.fishing_trip.quick_reservation.*;
import code.model.FishingTrip;
import code.model.FishingTripQuickReservation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FishingTripService {
    FishingTrip save(FishingTrip fishingTrip);
    FishingTrip edit(FishingTrip fishingTrip) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException;
    void editPictures(Integer id, MultipartFile[] pictures) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, IOException;
    void addQuickReservation(Integer id, FishingTripQuickReservation fishingTripQuickReservation) throws AddQuickReservationToAnotherInstructorFishingTripException, FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException, QuickReservationStartDateInPastException, ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException, FishingTripReservationTagsDontContainQuickReservationTagException, NoAvailablePeriodForQuickReservationException, QuickReservationOverlappingException, FishingTripNotFoundException;
}