package code.service;

import code.exceptions.entities.EntityNotDeletableException;
import code.exceptions.entities.EntityNotFoundException;

public interface BoatService {
    void throwExceptionIfBoatDontExist(Integer id) throws EntityNotFoundException;
    void checkIfBoatDeletable(Integer id) throws EntityNotDeletableException;
    void unlinkReferencesAndDeleteBoat(Integer id);
}
