package code.service;

import code.exceptions.admin.*;
import code.exceptions.entities.EntityNotDeletableException;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.UnexpectedUserRoleException;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.Admin;

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
}
