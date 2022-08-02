package code.service;

import code.exceptions.admin.ChangedPasswordException;
import code.exceptions.admin.ModifyAnotherUserDataException;
import code.exceptions.admin.NonMainAdminRegisterOtherAdminException;
import code.exceptions.admin.NotChangedPasswordException;
import code.exceptions.registration.EmailTakenException;
import code.exceptions.registration.UserNotFoundException;
import code.model.Admin;

public interface AdminService {
    Admin save(Admin admin) throws NonMainAdminRegisterOtherAdminException, EmailTakenException;
    Admin changePersonalData(Admin admin) throws UserNotFoundException, ModifyAnotherUserDataException, NotChangedPasswordException;
    void changePassword(Admin admin) throws ModifyAnotherUserDataException, UserNotFoundException, ChangedPasswordException;
}
