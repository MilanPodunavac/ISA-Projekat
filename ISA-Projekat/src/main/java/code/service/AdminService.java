package code.service;

import code.dto.ProviderRegistrationRequest;
import code.exceptions.admin.ModifyAnotherUserPersonalDataException;
import code.exceptions.admin.NonMainAdminRegisterOtherAdminException;
import code.exceptions.registration.EmailTakenException;
import code.exceptions.registration.UserNotFoundException;
import code.model.Admin;

public interface AdminService {
    Admin save(Admin admin) throws NonMainAdminRegisterOtherAdminException, EmailTakenException;
    Admin changePersonalData(Admin admin) throws UserNotFoundException, ModifyAnotherUserPersonalDataException;
}
