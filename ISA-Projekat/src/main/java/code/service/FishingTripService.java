package code.service;

import code.exceptions.fishing_trip.EditAnotherInstructorFishingTripException;
import code.exceptions.fishing_trip.FishingTripNotFoundException;
import code.model.FishingTrip;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FishingTripService {
    FishingTrip save(FishingTrip fishingTrip);
    FishingTrip edit(FishingTrip fishingTrip) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException;
    void editPictures(Integer id, MultipartFile[] pictures) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, IOException;
}
