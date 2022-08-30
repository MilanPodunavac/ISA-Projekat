package code.service;

import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.fishing_instructor.AddAvailablePeriodInPastException;
import code.exceptions.fishing_instructor.AvailablePeriodOverlappingException;
import code.exceptions.fishing_instructor.AvailablePeriodStartAfterEndDateException;
import code.exceptions.provider_registration.EmailTakenException;
import code.model.FishingInstructor;
import code.model.FishingInstructorAvailablePeriod;

import java.util.List;

public interface FishingInstructorService {
    FishingInstructor save(FishingInstructor fishingInstructor) throws EmailTakenException;
    void addAvailablePeriod(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod) throws AvailablePeriodStartAfterEndDateException, AvailablePeriodOverlappingException, AddAvailablePeriodInPastException;
    void changePersonalData(FishingInstructor fishingInstructor);
    void changePassword(FishingInstructor fishingInstructor);
    List<FishingInstructor> getAllFishingInstructors();
    FishingInstructor getFishingInstructor(Integer id) throws EntityNotFoundException;
    FishingInstructor getLoggedInInstructor();
}
