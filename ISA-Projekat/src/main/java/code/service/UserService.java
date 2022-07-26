package code.service;

import code.model.User;

import java.util.List;

public interface UserService {
    boolean userExists(String email);
    List<User> getUnverifiedProviders();
    void acceptRegistrationRequest(String email);
    void declineRegistrationRequest(String email, String declineReason);
}
