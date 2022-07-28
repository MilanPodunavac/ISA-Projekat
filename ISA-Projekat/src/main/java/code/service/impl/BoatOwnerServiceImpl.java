package code.service.impl;

import code.exceptions.registration.EmailTakenException;
import code.model.*;
import code.repository.BoatOwnerRepository;
import code.service.BoatOwnerService;
import code.service.RoleService;
import code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BoatOwnerServiceImpl implements BoatOwnerService {
    private final BoatOwnerRepository boatOwnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public BoatOwnerServiceImpl(UserService userService, BoatOwnerRepository boatOwnerRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.boatOwnerRepository = boatOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void save(BoatOwner boatOwner) throws EmailTakenException {
        userService.throwExceptionIfEmailExists(boatOwner.getEmail());
        saveRegistrationRequest(boatOwner);
    }

    private void saveRegistrationRequest(BoatOwner boatOwner) {
        boatOwner.setPassword(passwordEncoder.encode(boatOwner.getPassword()));
        boatOwner.setEnabled(false);

        Role role = roleService.findByName("ROLE_BOAT_OWNER");
        boatOwner.setRole(role);

        boatOwnerRepository.save(boatOwner);
    }
}
