package code.service;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.AvailabilityPeriod;
import code.model.cottage.Cottage;
import code.model.cottage.CottageAction;
import code.model.cottage.CottageReservation;

public interface CottageService {
    void addCottage(String email, Cottage cottage) throws UserNotFoundException, UnauthorizedAccessException;
    void addAvailabilityPeriod(int cottageId, AvailabilityPeriod period, String email) throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException;
    void addReservation(String clientEmail, int cottageId, CottageReservation reservation, String email) throws EntityNotFoundException, UserNotFoundException, InvalidReservationException, EntityNotOwnedException, EntityNotAvailableException, UnauthorizedAccessException;
    void throwExceptionIfCottageDontExist(Integer id) throws EntityNotFoundException;
    void checkIfCottageDeletable(Integer id) throws EntityNotDeletableException;
    void unlinkReferencesAndDeleteCottage(Integer id);
    void addAction(String ownerEmail, int cottageId, CottageAction action) throws UnauthorizedAccessException, EntityNotFoundException, EntityNotOwnedException, EntityNotAvailableException;
}
