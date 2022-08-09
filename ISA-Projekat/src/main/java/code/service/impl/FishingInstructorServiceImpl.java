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
        throwExceptionIfAvaiablePeriodStartAfterEndDate(fishingInstructorAvailablePeriod);
        throwExceptionIfAddAvailablePeriodInPast(fishingInstructorAvailablePeriod);
        FishingInstructor fishingInstructor = setFishingInstructor(fishingInstructorAvailablePeriod);
        throwExceptionIfAvailablePeriodOverlapping(fishingInstructorAvailablePeriod, fishingInstructor);
        _fishingInstructorAvailablePeriodRepository.save(fishingInstructorAvailablePeriod);
    }

    private void throwExceptionIfAvaiablePeriodStartAfterEndDate(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod) throws AvailablePeriodStartAfterEndDateException {
        if (fishingInstructorAvailablePeriod.getAvailableFrom().after(fishingInstructorAvailablePeriod.getAvailableTo())) {
            throw new AvailablePeriodStartAfterEndDateException("Start date can't be after end date!");
        }
    }

    private void throwExceptionIfAddAvailablePeriodInPast(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod) throws AddAvailablePeriodInPastException {
        if (fishingInstructorAvailablePeriod.getAvailableFrom().before(new Date())) {
            throw new AddAvailablePeriodInPastException("You can't add available period in the past!");
        }
    }

    private FishingInstructor setFishingInstructor(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        FishingInstructor fishingInstructor = (FishingInstructor) _userService.findById(user.getId());
        fishingInstructorAvailablePeriod.setFishingInstructor(fishingInstructor);
        return fishingInstructor;
    }

    private void throwExceptionIfAvailablePeriodOverlapping(FishingInstructorAvailablePeriod fishingInstructorAvailablePeriod, FishingInstructor fishingInstructor) throws AvailablePeriodOverlappingException {
        List<FishingInstructorAvailablePeriod> fishingInstructorAvailablePeriods = _fishingInstructorAvailablePeriodRepository.findByFishingInstructor(fishingInstructor.getId());
        for (FishingInstructorAvailablePeriod fiap : fishingInstructorAvailablePeriods) {
            if (((fiap.getAvailableFrom().before(fishingInstructorAvailablePeriod.getAvailableFrom()) || fiap.getAvailableFrom().equals(fishingInstructorAvailablePeriod.getAvailableFrom())) && fiap.getAvailableTo().after(fishingInstructorAvailablePeriod.getAvailableFrom())) || ((fishingInstructorAvailablePeriod.getAvailableFrom().before(fiap.getAvailableFrom()) || fishingInstructorAvailablePeriod.getAvailableFrom().equals(fiap.getAvailableFrom())) && (fishingInstructorAvailablePeriod.getAvailableTo().after(fiap.getAvailableTo()) || (fishingInstructorAvailablePeriod.getAvailableTo().equals(fiap.getAvailableTo())))) || (fishingInstructorAvailablePeriod.getAvailableTo().after(fiap.getAvailableFrom()) && (fishingInstructorAvailablePeriod.getAvailableTo().before(fiap.getAvailableTo()) || fishingInstructorAvailablePeriod.getAvailableTo().equals(fiap.getAvailableTo())))){
                throw new AvailablePeriodOverlappingException("Available period overlapping another!");
            }
        }
    }
}