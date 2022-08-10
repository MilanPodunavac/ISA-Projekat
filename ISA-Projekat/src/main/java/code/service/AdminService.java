package code.service;

import code.exceptions.admin.*;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.Admin;

public interface AdminService {
    Admin save(Admin admin) throws NonMainAdminRegisterOtherAdminException, EmailTakenException;
    Admin changePersonalData(Admin admin) throws UserNotFoundException, ModifyAnotherUserDataException, NotChangedPasswordException;
    void changePassword(Admin admin) throws ChangedPasswordException;
}
