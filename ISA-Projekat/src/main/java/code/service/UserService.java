package code.service;

import code.exceptions.admin.ModifyAnotherUserDataException;
import code.exceptions.entities.AccountDeletionRequestDontExistException;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.NotProviderException;
import code.exceptions.provider_registration.UserAccountActivatedException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.AccountDeletionRequest;
import code.model.CottageOwner;
import code.model.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    void throwExceptionIfEmailExists(String email) throws EmailTakenException;
    void throwExceptionIfModifyAnotherUserData(Integer id) throws ModifyAnotherUserDataException;
    void throwExceptionIfUserDontExist(Integer id) throws UserNotFoundException;
    List<User> getUnverifiedProviders();
    void acceptRegistrationRequest(Integer id) throws UserNotFoundException, UserAccountActivatedException, NotProviderException;
    void declineRegistrationRequest(Integer id, String declineReason) throws UserNotFoundException, UserAccountActivatedException, NotProviderException;
    void updatePersonalInformation(User user) throws UserNotFoundException;
    void changePassword(String newPassword, String email) throws UserNotFoundException;
    void submitAccountDeletionRequest(AccountDeletionRequest accountDeletionRequest);
    void declineAccountDeletionRequest(Integer id, String responseText) throws AccountDeletionRequestDontExistException;
}
