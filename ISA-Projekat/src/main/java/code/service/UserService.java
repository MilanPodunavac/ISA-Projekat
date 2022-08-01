package code.service;

import code.exceptions.admin.ModifyAnotherUserPersonalDataException;
import code.exceptions.admin.NonMainAdminRegisterOtherAdminException;
import code.exceptions.registration.EmailTakenException;
import code.exceptions.registration.UserAccountActivatedException;
import code.exceptions.registration.UserNotFoundException;
import code.model.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    void throwExceptionIfEmailExists(String email) throws EmailTakenException;
    void throwExceptionIfModifyAnotherUserPersonalData(Integer id) throws ModifyAnotherUserPersonalDataException;
    void throwExceptionIfUserDontExist(Integer id) throws UserNotFoundException;
    List<User> getUnverifiedProviders();
    void acceptRegistrationRequest(Integer id) throws UserNotFoundException, UserAccountActivatedException;
    void declineRegistrationRequest(Integer id, String declineReason) throws UserNotFoundException, UserAccountActivatedException;
}
