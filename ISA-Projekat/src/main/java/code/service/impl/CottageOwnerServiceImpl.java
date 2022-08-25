package code.service.impl;

import code.exceptions.provider_registration.EmailTakenException;
import code.model.*;
import code.model.cottage.CottageOwner;
import code.repository.CottageOwnerRepository;
import code.repository.LoyaltyProgramProviderRepository;
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
    private final LoyaltyProgramProviderRepository loyaltyProgramProviderRepository;

    @Autowired
    public CottageOwnerServiceImpl(UserService userService, CottageOwnerRepository cottageOwnerRepository, PasswordEncoder passwordEncoder, RoleService roleService, LoyaltyProgramProviderRepository loyaltyProgramProviderRepository) {
        this.userService = userService;
        this.cottageOwnerRepository = cottageOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.loyaltyProgramProviderRepository = loyaltyProgramProviderRepository;
    }

    @Override
    public void save(CottageOwner cottageOwner) throws EmailTakenException {
        userService.throwExceptionIfEmailExists(cottageOwner.getEmail());
        saveRegistrationRequest(cottageOwner);
    }

    private void saveRegistrationRequest(CottageOwner cottageOwner) {
        cottageOwner.setPassword(passwordEncoder.encode(cottageOwner.getPassword()));
        cottageOwner.setEnabled(false);
        cottageOwner.setLoyaltyPoints(0);
        cottageOwner.setCategory(loyaltyProgramProviderRepository.getById(1));

        Role role = roleService.findByName("ROLE_COTTAGE_OWNER");
        cottageOwner.setRole(role);

        cottageOwnerRepository.save(cottageOwner);
    }
}