package code.service;

import code.model.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    boolean isUserEnabled(Integer id);
    boolean userExists(Integer id);
    boolean userExists(String email);
    List<User> getUnverifiedProviders();
    void acceptRegistrationRequest(Integer id);
    void declineRegistrationRequest(Integer id, String declineReason);
}
