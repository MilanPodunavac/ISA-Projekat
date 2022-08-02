package code.service.impl;

import code.exceptions.provider_registration.EmailTakenException;
import code.model.*;
import code.repository.CottageOwnerRepository;
import code.service.CottageOwnerService;
import code.service.RoleService;
import code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CottageOwnerServiceImpl implements CottageOwnerService {
    private final CottageOwnerRepository cottageOwnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public CottageOwnerServiceImpl(UserService userService, CottageOwnerRepository cottageOwnerRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.cottageOwnerRepository = cottageOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void save(CottageOwner cottageOwner) throws EmailTakenException {
        userService.throwExceptionIfEmailExists(cottageOwner.getEmail());
        saveRegistrationRequest(cottageOwner);
    }

    private void saveRegistrationRequest(CottageOwner cottageOwner) {
        cottageOwner.setPassword(passwordEncoder.encode(cottageOwner.getPassword()));
        cottageOwner.setEnabled(false);

        Role role = roleService.findByName("ROLE_COTTAGE_OWNER");
        cottageOwner.setRole(role);

        cottageOwnerRepository.save(cottageOwner);
    }
}