package code.service;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.base.AvailabilityPeriod;
import code.model.base.OwnerCommentary;
import code.model.base.PictureBase64;
import code.model.boat.Boat;
import code.model.cottage.Cottage;
import code.model.cottage.CottageAction;
import code.model.cottage.CottageReservation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CottageService {
    void addCottage(String email, Cottage cottage) throws UserNotFoundException, UnauthorizedAccessException;
    void addAvailabilityPeriod(int cottageId, AvailabilityPeriod period, String email) throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException;
    void addReservation(String clientEmail, int cottageId, CottageReservation reservation, String email) throws EntityNotFoundException, UserNotFoundException, InvalidReservationException, EntityNotOwnedException, EntityNotAvailableException, UnauthorizedAccessException, ClientCancelledThisPeriodException;
    void throwExceptionIfCottageDontExist(Integer id) throws EntityNotFoundException;
    void checkIfCottageDeletable(Integer id) throws EntityNotDeletableException, EntityNotFoundException;
    void unlinkReferencesAndDeleteCottage(Integer id) throws EntityNotFoundException, EntityNotDeletableException;
    void addAction(String ownerEmail, int cottageId, CottageAction action) throws UnauthorizedAccessException, EntityNotFoundException, EntityNotOwnedException, EntityNotAvailableException, InvalidReservationException;
    void addPicture(int id, MultipartFile picture, String email) throws EntityNotFoundException, EntityNotOwnedException, IOException;
    void deletePicture(int id, int pic, String email) throws EntityNotOwnedException, EntityNotFoundException;
    void updateCottage(int id, Cottage updateCottage, String email) throws EntityNotFoundException, EntityNotOwnedException, EntityNotUpdateableException;
    void addReservationCommentary(int id, int resId, String email, OwnerCommentary commentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented;
    void addActionCommentary(int id, int actId, String email, OwnerCommentary commentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented;
    List<Cottage> getAllCottages();
    Cottage getCottage(Integer id) throws EntityNotFoundException;
    List<PictureBase64> getCottageImagesAsBase64(int id) throws EntityNotFoundException, IOException;
    CottageReservation getCottageReservation(int cottageId, int resId) throws EntityNotFoundException;

    CottageAction getCottageAction(Integer id, Integer actId) throws EntityNotFoundException;
}
