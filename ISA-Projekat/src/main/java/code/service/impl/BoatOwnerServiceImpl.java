package code.service.impl;

import code.dto.RegistrationRequest;
import code.model.*;
import code.repository.BoatOwnerRepository;
import code.service.BoatOwnerService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BoatOwnerServiceImpl implements BoatOwnerService {
    private final BoatOwnerRepository boatOwnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public BoatOwnerServiceImpl(BoatOwnerRepository boatOwnerRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.boatOwnerRepository = boatOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public BoatOwner save(BoatOwner boatOwner) {
        boatOwner.setPassword(passwordEncoder.encode(boatOwner.getPassword()));
        boatOwner.setEnabled(false);

        Role role = roleService.findByName("ROLE_BOAT_OWNER");
        boatOwner.setRole(role);

        return this.boatOwnerRepository.save(boatOwner);
    }

}
