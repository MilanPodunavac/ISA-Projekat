package code.service;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.AvailabilityPeriod;
import code.model.Cottage;
import code.model.CottageReservation;

public interface CottageService {
    void addCottage(String email, Cottage cottage) throws UserNotFoundException;
    void addAvailabilityPeriod(int cottageId, AvailabilityPeriod period, String email) throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotFoundException, EntityNotOwnedException;
    void addReservation(String clientEmail, int cottageId, CottageReservation reservation, String email) throws EntityNotFoundException, UserNotFoundException, InvalidReservationException, EntityNotOwnedException, EntityNotAvailableException;
}