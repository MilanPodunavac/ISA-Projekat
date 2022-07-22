package code.service;

import code.dto.UserRequest;
import code.model.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    User findByEmail(String email);
    List<User> findAll();
    User save(UserRequest userRequest);
}
