package code.service.impl;

import code.dto.fishing_instructor.PeriodicalReservations;
import code.dto.fishing_instructor.ProfitInInterval;
import code.exceptions.entities.EntityBadRequestException;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.EntityNotOwnedException;
import code.exceptions.fishing_instructor.AddAvailablePeriodInPastException;
import code.exceptions.fishing_instructor.AvailablePeriodOverlappingException;
import code.exceptions.fishing_instructor.AvailablePeriodStartAfterEndDateException;
import code.exceptions.provider_registration.EmailTakenException;
import code.model.*;
import code.model.boat.Boat;
import code.repository.*;
import code.service.FishingInstructorService;
import code.service.RoleService;
import code.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FishingInstructorServiceImpl implements FishingInstructorService {
    private final FishingInstructorRepository _fishingInstructorRepository;
    private final FishingInstructorAvailablePeriodRepository _fishingInstructorAvailablePeriodRepository;
    private final PasswordEncoder _passwordEncoder;
    private final RoleService _roleService;
    private final UserService _userService;
    private final LoyaltyProgramProviderRepository _loyaltyProgramProviderRepository;
    private final IncomeRecordRepository _incomeRecordRepository;
    private final FishingTripReservationRepository _fishingTripReservationRepository;
    private final FishingTripQuickReservationRepository _fishingTripQuickReservationRepository;
    private final FishingTripRepository _fishingTripRepository;
    private final ClientRepository _clientRepository;
    private final ReviewFishingTripRepository _reviewRepository;
    private final ComplaintFishingInstructorRepository _complaintRepository;

    public FishingInstructorServiceImpl(UserService userService, FishingInstructorRepository fishingInstructorRepository, FishingInstructorAvailablePeriodRepository fishingInstructorAvailablePeriodRepository, PasswordEncoder passwordEncoder, RoleService roleService, LoyaltyProgramProviderRepository loyaltyProgramProviderRepository, IncomeRecordRepository incomeRecordRepository, FishingTripReservationRepository fishingTripReservationRepository, FishingTripQuickReservationRepository fishingTripQuickReservationRepository, FishingTripRepository fishingTripRepository, ClientRepository clientRepository, ReviewFishingTripRepository reviewFishingTripRepository, ComplaintFishingInstructorRepository complaintFishingInstructorRepository) {
        this._userService = userService;
        this._fishingInstructorRepository = fishingInstructorRepository;
        this._fishingInstructorAvailablePeriodRepository = fishingInstructorAvailablePeriodRepository;
        this._passwordEncoder = passwordEncoder;
        this._roleService = roleService;
        this._loyaltyProgramProviderRepository = loyaltyProgramProviderRepository;
        this._incomeRecordRepository = incomeRecordRepository;
        this._fishingTripQuickReservationRepository = fishingTripQuickReservationRepository;
        this._fishingTripReservationRepository = fishingTripReservationRepository;
        this._fishingTripRepository = fishingTripRepository;
        this._clientRepository = clientRepository;
        this._complaintRepository = complaintFishingInstructorRepository;
        this._reviewRepository = reviewFishingTripRepository;
    }

    @Override
    public FishingInstructor save(FishingInstructor fishingInstructor) throws EmailTakenException {
        _userService.throwExceptionIfEmailExists(fishingInstructor.getEmail());
        return saveRegistrationRequest(fishingInstructor);
    }

    private FishingInstructor saveRegistrationRequest(FishingInstructor fishingInstructor) {
            fishingInstructor.setPassword(_passwordEncoder.encode(fishingInstructor.getPassword()));
            fishingInstructor.setEnabled(false);
            fishingInstructor.setLoyaltyPoints(0);
            fishingInstructor.setCategory(_loyaltyProgramProviderRepository.getById(1));

            Role role = _roleService.findByName("ROLE_FISHING_INSTRUCTOR");
            fishingInstructor.setRole(role);

            return _fishingInstructorRepository.save(fishingInstructor);
    }

    @Override
    public void addAvailablePeriod(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod) throws AvailablePeriodStartAfterEndDateException, AvailablePeriodOverlappingException, AddAvailablePeriodInPastException {
        throwExceptionIfAvailablePeriodStartAfterEndDate(fishingInstructorAvailablePeriod);
        throwExceptionIfAddAvailablePeriodInPast(fishingInstructorAvailablePeriod);
        FishingInstructor fishingInstructor = setFishingInstructor(fishingInstructorAvailablePeriod);
        throwExceptionIfAvailablePeriodOverlapping(fishingInstructorAvailablePeriod, fishingInstructor);
        _fishingInstructorAvailablePeriodRepository.save(fishingInstructorAvailablePeriod);
    }

    private void throwExceptionIfAvailablePeriodStartAfterEndDate(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod) throws AvailablePeriodStartAfterEndDateException {
        if (fishingInstructorAvailablePeriod.getAvailableFrom().isAfter(fishingInstructorAvailablePeriod.getAvailableTo())) {
            throw new AvailablePeriodStartAfterEndDateException("Start date can't be after end date!");
        }
    }

    private void throwExceptionIfAddAvailablePeriodInPast(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod) throws AddAvailablePeriodInPastException {
        if (fishingInstructorAvailablePeriod.getAvailableFrom().isBefore(LocalDate.now())) {
            throw new AddAvailablePeriodInPastException("You can't add available period in the past!");
        }
    }

    private FishingInstructor setFishingInstructor(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod) {
        FishingInstructor fishingInstructor = getLoggedInFishingInstructor();
        fishingInstructorAvailablePeriod.setFishingInstructor(fishingInstructor);
        return fishingInstructor;
    }

    private FishingInstructor getLoggedInFishingInstructor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return (FishingInstructor) _userService.findById(user.getId());
    }

    private void throwExceptionIfAvailablePeriodOverlapping(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod, FishingInstructor fishingInstructor) throws AvailablePeriodOverlappingException {
        List<FishingInstructorAvailablePeriod> fishingInstructorAvailablePeriods = _fishingInstructorAvailablePeriodRepository.findByFishingInstructor(fishingInstructor.getId());
        for (FishingInstructorAvailablePeriod fiap : fishingInstructorAvailablePeriods) {
            if (((fiap.getAvailableFrom().isBefore(fishingInstructorAvailablePeriod.getAvailableFrom()) || fiap.getAvailableFrom().isEqual(fishingInstructorAvailablePeriod.getAvailableFrom())) && (fiap.getAvailableTo().isAfter(fishingInstructorAvailablePeriod.getAvailableFrom()) || fiap.getAvailableTo().isEqual(fishingInstructorAvailablePeriod.getAvailableFrom()))) || ((fishingInstructorAvailablePeriod.getAvailableFrom().isBefore(fiap.getAvailableFrom()) || fishingInstructorAvailablePeriod.getAvailableFrom().isEqual(fiap.getAvailableFrom())) && (fishingInstructorAvailablePeriod.getAvailableTo().isAfter(fiap.getAvailableTo()) || (fishingInstructorAvailablePeriod.getAvailableTo().isEqual(fiap.getAvailableTo())))) || ((fishingInstructorAvailablePeriod.getAvailableTo().isAfter(fiap.getAvailableFrom()) || fishingInstructorAvailablePeriod.getAvailableTo().isEqual(fiap.getAvailableFrom())) && (fishingInstructorAvailablePeriod.getAvailableTo().isBefore(fiap.getAvailableTo()) || fishingInstructorAvailablePeriod.getAvailableTo().isEqual(fiap.getAvailableTo())))){
                throw new AvailablePeriodOverlappingException("Available period overlapping another!");
            }
        }
    }

    @Override
    public void changePersonalData(FishingInstructor fishingInstructor) {
        FishingInstructor loggedInFishingInstructor = getLoggedInFishingInstructor();
        changePersonalDataFields(loggedInFishingInstructor, fishingInstructor);
    }

    private void changePersonalDataFields(FishingInstructor loggedInFishingInstructor, FishingInstructor fishingInstructor) {
        loggedInFishingInstructor.setFirstName(fishingInstructor.getFirstName());
        loggedInFishingInstructor.setLastName(fishingInstructor.getLastName());
        loggedInFishingInstructor.setPhoneNumber(fishingInstructor.getPhoneNumber());
        loggedInFishingInstructor.getLocation().setCountryName(fishingInstructor.getLocation().getCountryName());
        loggedInFishingInstructor.getLocation().setCityName(fishingInstructor.getLocation().getCityName());
        loggedInFishingInstructor.getLocation().setStreetName(fishingInstructor.getLocation().getStreetName());
        loggedInFishingInstructor.getLocation().setLongitude(fishingInstructor.getLocation().getLongitude());
        loggedInFishingInstructor.getLocation().setLatitude(fishingInstructor.getLocation().getLatitude());
        loggedInFishingInstructor.setBiography(fishingInstructor.getBiography());
        _fishingInstructorRepository.save(loggedInFishingInstructor);
    }

    @Override
    public void changePassword(FishingInstructor fishingInstructor) {
        FishingInstructor loggedInFishingInstructor = getLoggedInFishingInstructor();
        changePasswordField(loggedInFishingInstructor, fishingInstructor);
    }

    private void changePasswordField(FishingInstructor loggedInFishingInstructor, FishingInstructor fishingInstructor) {
        loggedInFishingInstructor.setPassword(_passwordEncoder.encode(fishingInstructor.getPassword()));
        _fishingInstructorRepository.save(loggedInFishingInstructor);
    }

    @Override
    public List<FishingInstructor> getAllFishingInstructors(){
        return _fishingInstructorRepository.findAll();
    }
    @Override
    public FishingInstructor getFishingInstructor(Integer id) throws EntityNotFoundException {
        FishingInstructor fishingInstructor = _fishingInstructorRepository.findById(id).orElse(null);
        if(fishingInstructor == null) {
            throw new EntityNotFoundException("Fishing instructor doesn't exist!");
        }
        return fishingInstructor;
    }

    @Override
    public FishingInstructor getLoggedInInstructor() {
        return getLoggedInFishingInstructor();
    }

    @Override
    public List<FishingInstructorAvailablePeriod> getFishingInstructorAvailablePeriods() {
        return _fishingInstructorAvailablePeriodRepository.findByFishingInstructor(getLoggedInFishingInstructor().getId());
    }

    @Override
    public String getIncomeInTimeInterval(ProfitInInterval profitInInterval) throws EntityBadRequestException {
        if (profitInInterval.getTo().isBefore(profitInInterval.getFrom())) {
            throw new EntityBadRequestException("End date can't be before start date!");
        }

        List<IncomeRecord> incomeRecords = _incomeRecordRepository.findByReservationProviderId(getLoggedInInstructor().getId());
        double totalProfit = 0;
        for (IncomeRecord incomeRecord : incomeRecords) {
            if ((incomeRecord.getDateOfEntry().isAfter(profitInInterval.getFrom()) || incomeRecord.getDateOfEntry().isEqual(profitInInterval.getFrom())) && (incomeRecord.getDateOfEntry().isBefore(profitInInterval.getTo()) || incomeRecord.getDateOfEntry().isEqual(profitInInterval.getTo()))) {
                totalProfit += incomeRecord.getProviderIncome();
            }
        }

        return totalProfit + "";
    }

    @Override
    public List<PeriodicalReservations> weeklyReservations() {
        LocalDate startingPoint = LocalDate.now().minusDays(7);
        List<Integer> instructorFishingTripsIds = _fishingTripRepository.findByFishingInstructor(getLoggedInInstructor().getId());
        List<FishingTripReservation> instructorFishingTripReservations = _fishingTripReservationRepository.findByFishingTripIdIn(instructorFishingTripsIds);
        List<FishingTripQuickReservation> instructorFishingTripQuickReservations = _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripsIds);
        List<PeriodicalReservations> periodicalReservationsList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int dailyReservations = 0;
            for (FishingTripReservation fishingTripReservation : instructorFishingTripReservations) {
                if (startingPoint.plusDays(i).isEqual(fishingTripReservation.getStart())) {
                    dailyReservations++;
                }
            }

            for (FishingTripQuickReservation fishingTripQuickReservation : instructorFishingTripQuickReservations) {
                if (fishingTripQuickReservation.getClient() != null && startingPoint.plusDays(i).isEqual(fishingTripQuickReservation.getStart())) {
                    dailyReservations++;
                }
            }

            periodicalReservationsList.add(new PeriodicalReservations(startingPoint.plusDays(i), dailyReservations));
        }

        return periodicalReservationsList;
    }

    @Override
    public List<PeriodicalReservations> monthlyReservations() {
        LocalDate startingPoint = LocalDate.now().minusDays(28);
        List<Integer> instructorFishingTripsIds = _fishingTripRepository.findByFishingInstructor(getLoggedInInstructor().getId());
        List<FishingTripReservation> instructorFishingTripReservations = _fishingTripReservationRepository.findByFishingTripIdIn(instructorFishingTripsIds);
        List<FishingTripQuickReservation> instructorFishingTripQuickReservations = _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripsIds);
        List<PeriodicalReservations> periodicalReservationsList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int weeklyReservations = 0;
            for (FishingTripReservation fishingTripReservation : instructorFishingTripReservations) {
                if ((startingPoint.plusDays(i * 7).isBefore(fishingTripReservation.getStart()) || startingPoint.plusDays(i * 7).isEqual(fishingTripReservation.getStart())) && (startingPoint.plusDays(i * 7 + 6).isAfter(fishingTripReservation.getStart()) || startingPoint.plusDays(i * 7 + 6).isEqual(fishingTripReservation.getStart()))) {
                    weeklyReservations++;
                }
            }

            for (FishingTripQuickReservation fishingTripQuickReservation : instructorFishingTripQuickReservations) {
                if (fishingTripQuickReservation.getClient() != null && (startingPoint.plusDays(i * 7).isBefore(fishingTripQuickReservation.getStart()) || startingPoint.plusDays(i * 7).isEqual(fishingTripQuickReservation.getStart())) && (startingPoint.plusDays(i * 7 + 6).isAfter(fishingTripQuickReservation.getStart()) || startingPoint.plusDays(i * 7 + 6).isEqual(fishingTripQuickReservation.getStart()))) {
                    weeklyReservations++;
                }
            }

            periodicalReservationsList.add(new PeriodicalReservations(startingPoint.plusDays(i * 7), weeklyReservations));
        }

        return periodicalReservationsList;
    }

    @Override
    public List<PeriodicalReservations> yearlyReservations() {
        LocalDate startingPoint = LocalDate.now().minusYears(1);
        startingPoint = startingPoint.withDayOfMonth(1);
        List<Integer> instructorFishingTripsIds = _fishingTripRepository.findByFishingInstructor(getLoggedInInstructor().getId());
        List<FishingTripReservation> instructorFishingTripReservations = _fishingTripReservationRepository.findByFishingTripIdIn(instructorFishingTripsIds);
        List<FishingTripQuickReservation> instructorFishingTripQuickReservations = _fishingTripQuickReservationRepository.findByFishingTripIdIn(instructorFishingTripsIds);
        List<PeriodicalReservations> periodicalReservationsList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int yearlyReservations = 0;
            for (FishingTripReservation fishingTripReservation : instructorFishingTripReservations) {
                if ((startingPoint.plusMonths(i).isBefore(fishingTripReservation.getStart()) || startingPoint.plusMonths(i).isEqual(fishingTripReservation.getStart())) && (startingPoint.plusMonths(i + 1).minusDays(1).isAfter(fishingTripReservation.getStart()) || startingPoint.plusMonths(i + 1).minusDays(1).isEqual(fishingTripReservation.getStart()))) {
                    yearlyReservations++;
                }
            }

            for (FishingTripQuickReservation fishingTripQuickReservation : instructorFishingTripQuickReservations) {
                if (fishingTripQuickReservation.getClient() != null && (startingPoint.plusMonths(i).isBefore(fishingTripQuickReservation.getStart()) || startingPoint.plusMonths(i).isEqual(fishingTripQuickReservation.getStart())) && (startingPoint.plusMonths(i + 1).minusDays(1).isAfter(fishingTripQuickReservation.getStart()) || startingPoint.plusMonths(i + 1).minusDays(1).isEqual(fishingTripQuickReservation.getStart()))) {
                    yearlyReservations++;
                }
            }

            periodicalReservationsList.add(new PeriodicalReservations(startingPoint.plusMonths(i), yearlyReservations));
        }

        return periodicalReservationsList;
    }

    public void addReview(int instructorId, int clientId, int grade, String description) throws EntityNotFoundException, EntityNotOwnedException {
        Client client = _clientRepository.findById(clientId).get();
        FishingInstructor fishingInstructor = _fishingInstructorRepository.findById(instructorId).get();
        ReviewFishingTrip review = new ReviewFishingTrip();
        review.setDescription(description);
        review.setGrade(grade);
        review.setClient(client);
        review.setFishingTrip(fishingInstructor.getFishingTrips().stream().findFirst().get());
        _reviewRepository.save(review);
    }

    @Override
    public void addComplaint(int instructorId, int clientId, String description) throws EntityNotFoundException, EntityNotOwnedException {
        Client client = _clientRepository.findById(clientId).get();
        FishingInstructor fishingInstructor = _fishingInstructorRepository.findById(instructorId).get();
        ComplaintFishingInstructor complaint = new ComplaintFishingInstructor();
        complaint.setDescription(description);
        complaint.setClient(client);
        complaint.setFishingInstructor(fishingInstructor);
        _complaintRepository.save(complaint);
    }
}