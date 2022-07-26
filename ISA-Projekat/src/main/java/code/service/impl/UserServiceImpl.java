package code.service.impl;

import code.model.Role;
import code.repository.RoleRepository;
import code.repository.UserRepository;
import code.service.RoleService;
import code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository _userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this._userRepository = userRepository;
    }

    @Override
    public boolean userExists(String email) {
        if (_userRepository.findByEmail(email) != null) {
            return true;
        }
        return false;
    }
}