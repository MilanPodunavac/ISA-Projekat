package code.service;

import code.dto.fishing_instructor.PeriodicalReservations;
import code.dto.fishing_instructor.ProfitInInterval;
import code.exceptions.entities.*;
import code.exceptions.fishing_instructor.AddAvailablePeriodInPastException;
import code.exceptions.fishing_instructor.AvailablePeriodOverlappingException;
import code.exceptions.fishing_instructor.AvailablePeriodStartAfterEndDateException;
import code.exceptions.provider_registration.EmailTakenException;
import code.model.FishingInstructor;
import code.model.FishingInstructorAvailablePeriod;

import java.util.Collection;
import java.util.List;

public interface FishingInstructorService {
    FishingInstructor save(FishingInstructor fishingInstructor) throws EmailTakenException;
    void addAvailablePeriod(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod) throws AvailablePeriodStartAfterEndDateException, AvailablePeriodOverlappingException, AddAvailablePeriodInPastException;
    void changePersonalData(FishingInstructor fishingInstructor);
    void changePassword(FishingInstructor fishingInstructor);
    List<FishingInstructor> getAllFishingInstructors();
    FishingInstructor getFishingInstructor(Integer id) throws EntityNotFoundException;
    FishingInstructor getLoggedInInstructor();
    List<FishingInstructorAvailablePeriod> getFishingInstructorAvailablePeriods();
    String getIncomeInTimeInterval(ProfitInInterval profitInInterval) throws EntityBadRequestException;
    void addReview(int fishingInstructorId, int clientId, int grade, String description) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented;
    void addComplaint(int fishingInstructorId, int clientId, String description) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented;
    List<PeriodicalReservations> weeklyReservations();
    List<PeriodicalReservations> monthlyReservations();
    List<PeriodicalReservations> yearlyReservations();
}
