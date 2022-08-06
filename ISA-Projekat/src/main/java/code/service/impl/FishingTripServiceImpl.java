package code.service.impl;

import code.exceptions.fishing_trip.EditAnotherInstructorFishingTripException;
import code.exceptions.fishing_trip.FishingTripNotFoundException;
import code.model.*;
import code.repository.FishingTripRepository;
import code.service.FishingTripService;
import code.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FishingTripServiceImpl implements FishingTripService {
    private final FishingTripRepository _fishingTripRepository;
    private final UserService _userService;

    public FishingTripServiceImpl(FishingTripRepository fishingTripRepository, UserService userService) {
        this._fishingTripRepository = fishingTripRepository;
        this._userService = userService;
    }

    @Override
    public FishingTrip save(FishingTrip fishingTrip) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        FishingInstructor fishingInstructor = (FishingInstructor) _userService.findById(user.getId());
        fishingTrip.setFishingInstructor(fishingInstructor);

        return _fishingTripRepository.save(fishingTrip);
    }

    @Transactional
    @Override
    public FishingTrip edit(FishingTrip fishingTrip) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException {
        throwExceptionIfFishingTripNotFound(fishingTrip.getId());
        throwExceptionIfEditAnotherInstructorFishingTripOrSetInstructor(fishingTrip);
        setLocationId(fishingTrip);
        return _fishingTripRepository.save(fishingTrip);
    }

    private void throwExceptionIfFishingTripNotFound(Integer fishingTripId) throws FishingTripNotFoundException {
        Optional<FishingTrip> fishingTrip = _fishingTripRepository.findById(fishingTripId);
        if (!fishingTrip.isPresent()) {
            throw new FishingTripNotFoundException("Fishing trip not found!");
        }
    }

    private void throwExceptionIfEditAnotherInstructorFishingTripOrSetInstructor(FishingTrip fishingTrip) throws EditAnotherInstructorFishingTripException {
        FishingTrip ft = _fishingTripRepository.getById(fishingTrip.getId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        FishingInstructor fishingInstructor = (FishingInstructor) _userService.findById(user.getId());
        if (fishingInstructor.getId() != ft.getFishingInstructor().getId()) {
            throw new EditAnotherInstructorFishingTripException("You can't edit another instructor's fishing trip!");
        } else {
            fishingTrip.setFishingInstructor(fishingInstructor);
        }
    }

    private void setLocationId(FishingTrip fishingTrip) {
        FishingTrip ft = _fishingTripRepository.getById(fishingTrip.getId());
        fishingTrip.getLocation().setId(ft.getLocation().getId());
    }
}
