package code.service;

import code.exceptions.entities.EntityNotDeletableException;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.boat.Boat;

public interface BoatService {
    void throwExceptionIfBoatDontExist(Integer id) throws EntityNotFoundException;
    void checkIfBoatDeletable(Integer id) throws EntityNotDeletableException;
    void unlinkReferencesAndDeleteBoat(Integer id);
    void addBoat(Boat boat) throws UnauthorizedAccessException, UserNotFoundException;
}
