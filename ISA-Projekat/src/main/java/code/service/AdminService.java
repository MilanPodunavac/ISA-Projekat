package code.service;

import code.dto.admin.ComplaintResponse;
import code.exceptions.admin.*;
import code.exceptions.entities.*;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;

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
    void currentPointsClientGetsAfterReservation(CurrentPointsClientGetsAfterReservation currentPointsClientGetsAfterReservation) throws NotChangedPasswordException;
    void currentPointsProviderGetsAfterReservation(CurrentPointsProviderGetsAfterReservation currentPointsProviderGetsAfterReservation) throws NotChangedPasswordException;
    void changeClientPointsNeededForLoyaltyProgramCategory(Integer id, LoyaltyProgramClient loyaltyProgramClient) throws NotChangedPasswordException, EntityNotUpdateableException;
    void changeProviderPointsNeededForLoyaltyProgramCategory(Integer id, LoyaltyProgramProvider loyaltyProgramProvider) throws NotChangedPasswordException, EntityNotUpdateableException;
    void respondToComplaint(Integer id, ComplaintResponse complaintResponse) throws EntityNotFoundException;
    void respondToComplaintFishingInstructor(Integer id, ComplaintResponse complaintResponse) throws EntityNotFoundException;
    void acceptReview(Integer id) throws EntityNotFoundException, EntityNotUpdateableException;
    void acceptReviewFishingTrip(Integer id) throws EntityNotFoundException, EntityNotUpdateableException;
    void declineReview(Integer id) throws EntityNotFoundException;
    void declineReviewFishingTrip(Integer id) throws EntityNotFoundException;
}
