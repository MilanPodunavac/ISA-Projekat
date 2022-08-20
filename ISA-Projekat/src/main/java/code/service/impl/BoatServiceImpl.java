package code.service.impl;

import code.exceptions.entities.EntityNotDeletableException;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.User;
import code.model.boat.Boat;
import code.model.boat.BoatOwner;
import code.repository.BoatRepository;
import code.service.BoatService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoatServiceImpl  implements BoatService {
    private final BoatRepository _boatRepository;

    public BoatServiceImpl(BoatRepository boatRepository){
        _boatRepository = boatRepository;
    }

    @Override
    public void throwExceptionIfBoatDontExist(Integer id) throws EntityNotFoundException {
        Optional<Boat> boat = _boatRepository.findById(id);
        if (!boat.isPresent()) {
            throw new EntityNotFoundException("Boat doesn't exist!");
        }
    }

    @Override
    public void checkIfBoatDeletable(Integer id) throws EntityNotDeletableException {

    }

    @Override
    public void unlinkReferencesAndDeleteBoat(Integer id) {

    }

    @Override
    public void addBoat(Boat boat) throws UnauthorizedAccessException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Cottage owner not found");
        owner.addBoat(boat);
        _boatRepository.save(boat);
    }
}
