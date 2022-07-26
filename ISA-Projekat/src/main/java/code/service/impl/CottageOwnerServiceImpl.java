package code.service.impl;

import code.model.*;
import code.repository.CottageOwnerRepository;
import code.service.CottageOwnerService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CottageOwnerServiceImpl implements CottageOwnerService {
    private final CottageOwnerRepository cottageOwnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public CottageOwnerServiceImpl(CottageOwnerRepository cottageOwnerRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.cottageOwnerRepository = cottageOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public CottageOwner save(CottageOwner cottageOwner) {
        cottageOwner.setPassword(passwordEncoder.encode(cottageOwner.getPassword()));
        cottageOwner.setEnabled(false);

        Role role = roleService.findByName("ROLE_COTTAGE_OWNER");
        cottageOwner.setRole(role);

        return this.cottageOwnerRepository.save(cottageOwner);
    }

}