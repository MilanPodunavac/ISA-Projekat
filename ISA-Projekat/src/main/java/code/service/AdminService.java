package code.service;

import code.exceptions.admin.*;
import code.exceptions.entities.CommentaryNotApprovableException;
import code.exceptions.entities.EntityNotDeletableException;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.UnexpectedUserRoleException;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.Admin;
import code.model.CurrentSystemTaxPercentage;

public interface AdminService {
    Admin save(Admin admin) throws NonMainAdminRegisterOtherAdminException, EmailTakenException;
    Admin changePersonalData(Admin admin) throws NotChangedPasswordException;
    void changePassword(Admin admin) throws ChangedPasswordException;
    void deleteFishingInstructor(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException;
    void deleteCottageOwner(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException;
    void deleteBoatOwner(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException;
    void deleteClient(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException;
    void deleteCottage(Integer id) throws NotChangedPasswordException, EntityNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException;
    void deleteBoat(Integer id) throws NotChangedPasswordException, EntityNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException;
    void fishingReservationCommentaryAccept(Integer reservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException;
    void fishingReservationCommentaryDecline(Integer reservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException;
    void fishingQuickReservationCommentaryAccept(Integer quickReservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException;
    void fishingQuickReservationCommentaryDecline(Integer quickReservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException;
    void reservationCommentaryAccept(Integer reservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException;
    void reservationCommentaryDecline(Integer reservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException;
    void quickReservationCommentaryAccept(Integer quickReservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException;
    void quickReservationCommentaryDecline(Integer quickReservationId) throws EntityNotFoundException, NotChangedPasswordException, CommentaryNotApprovableException;
    void changeCurrentSystemTaxPercentage(CurrentSystemTaxPercentage currentSystemTaxPercentage) throws NotChangedPasswordException;
}
