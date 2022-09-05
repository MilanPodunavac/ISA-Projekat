package code.service;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.base.AvailabilityPeriod;
import code.model.base.OwnerCommentary;
import code.model.base.PictureBase64;
import code.model.boat.Boat;
import code.model.boat.BoatAction;
import code.model.boat.BoatReservation;
import code.model.cottage.Cottage;
import code.model.cottage.CottageAction;
import code.model.cottage.CottageReservation;
import code.model.report.VisitReport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;

public interface BoatService {
    void throwExceptionIfBoatDontExist(Integer id) throws EntityNotFoundException;
    void checkIfBoatDeletable(Integer id) throws EntityNotDeletableException, EntityNotFoundException;
    void unlinkReferencesAndDeleteBoat(Integer id) throws EntityNotFoundException, EntityNotDeletableException, UnauthorizedAccessException, UserNotFoundException, EntityNotOwnedException;
    void addBoat(Boat boat) throws UnauthorizedAccessException, UserNotFoundException;
    void addAvailabilityPeriod(int boatId, AvailabilityPeriod period) throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException;
    void addReservation(String clientEmail, int boatId, BoatReservation reservation) throws EntityNotFoundException, UserNotFoundException, InvalidReservationException, EntityNotOwnedException, EntityNotAvailableException, UnauthorizedAccessException, ClientCancelledThisPeriodException;
    void addAction(int boatId, BoatAction action) throws UnauthorizedAccessException, EntityNotFoundException, EntityNotOwnedException, EntityNotAvailableException, InvalidReservationException, UserNotFoundException;
    void addPicture(int id, MultipartFile picture) throws EntityNotFoundException, EntityNotOwnedException, IOException, UnauthorizedAccessException, UserNotFoundException;
    void deletePicture(int id, int pic) throws EntityNotOwnedException, EntityNotFoundException, UnauthorizedAccessException, UserNotFoundException;
    void updateBoat(int id, Boat updateBoat) throws EntityNotFoundException, EntityNotOwnedException, EntityNotUpdateableException, UnauthorizedAccessException, UserNotFoundException;
    void addReservationCommentary(int id, int resId, OwnerCommentary commentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented, UnauthorizedAccessException, UserNotFoundException;
    void addActionCommentary(int id, int actId, OwnerCommentary commentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented, UnauthorizedAccessException, UserNotFoundException;
    List<Boat> getAllBoats();
    Boat getBoat(Integer id) throws EntityNotFoundException;
    List<PictureBase64> getBoatImagesAsBase64(int id) throws EntityNotFoundException, IOException;
    BoatReservation getBoatReservation(int boatId, int resId) throws EntityNotFoundException;

    BoatAction getBoatAction(Integer id, Integer actId) throws EntityNotFoundException;
    VisitReport generateVisitReport(int id) throws EntityNotOwnedException, EntityNotFoundException, UnauthorizedAccessException, UserNotFoundException;
}
