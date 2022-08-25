package code.service.impl;

import code.exceptions.provider_registration.EmailTakenException;
import code.model.*;
import code.model.boat.BoatOwner;
import code.repository.BoatOwnerRepository;
import code.repository.LoyaltyProgramProviderRepository;
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
    private final LoyaltyProgramProviderRepository loyaltyProgramProviderRepository;

    @Autowired
    public BoatOwnerServiceImpl(UserService userService, BoatOwnerRepository boatOwnerRepository, PasswordEncoder passwordEncoder, RoleService roleService, LoyaltyProgramProviderRepository loyaltyProgramProviderRepository) {
        this.userService = userService;
        this.boatOwnerRepository = boatOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.loyaltyProgramProviderRepository = loyaltyProgramProviderRepository;
    }

    @Override
    public void save(BoatOwner boatOwner) throws EmailTakenException {
        userService.throwExceptionIfEmailExists(boatOwner.getEmail());
        saveRegistrationRequest(boatOwner);
    }

    private void saveRegistrationRequest(BoatOwner boatOwner) {
        boatOwner.setPassword(passwordEncoder.encode(boatOwner.getPassword()));
        boatOwner.setEnabled(false);
        boatOwner.setLoyaltyPoints(0);
        boatOwner.setCategory(loyaltyProgramProviderRepository.getById(1));

        Role role = roleService.findByName("ROLE_BOAT_OWNER");
        boatOwner.setRole(role);

        boatOwnerRepository.save(boatOwner);
    }
}
