package code.service;

import code.model.Client;
import code.model.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User findById(Integer id);
    List<User> findAll ();
}
