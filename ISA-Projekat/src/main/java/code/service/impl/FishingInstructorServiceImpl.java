package code.service.impl;

import code.exceptions.fishing_instructor.AddAvailablePeriodInPastException;
import code.exceptions.fishing_instructor.AvailablePeriodOverlappingException;
import code.exceptions.fishing_instructor.AvailablePeriodStartAfterEndDateException;
import code.exceptions.provider_registration.EmailTakenException;
import code.model.*;
import code.repository.FishingInstructorAvailablePeriodRepository;
import code.repository.FishingInstructorRepository;
import code.service.FishingInstructorService;
import code.service.RoleService;
import code.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class FishingInstructorServiceImpl implements FishingInstructorService {
    private final FishingInstructorRepository _fishingInstructorRepository;
    private final FishingInstructorAvailablePeriodRepository _fishingInstructorAvailablePeriodRepository;
    private final PasswordEncoder _passwordEncoder;
    private final RoleService _roleService;
    private final UserService _userService;

    public FishingInstructorServiceImpl(UserService userService, FishingInstructorRepository fishingInstructorRepository, FishingInstructorAvailablePeriodRepository fishingInstructorAvailablePeriodRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this._userService = userService;
        this._fishingInstructorRepository = fishingInstructorRepository;
        this._fishingInstructorAvailablePeriodRepository = fishingInstructorAvailablePeriodRepository;
        this._passwordEncoder = passwordEncoder;
        this._roleService = roleService;
    }

    @Override
    public FishingInstructor save(FishingInstructor fishingInstructor) throws EmailTakenException {
        _userService.throwExceptionIfEmailExists(fishingInstructor.getEmail());
        return saveRegistrationRequest(fishingInstructor);
    }

    private FishingInstructor saveRegistrationRequest(FishingInstructor fishingInstructor) {
            fishingInstructor.setPassword(_passwordEncoder.encode(fishingInstructor.getPassword()));
            fishingInstructor.setEnabled(false);

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
}