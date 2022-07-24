package code.service.impl;

import code.dto.RegistrationRequest;
import code.model.*;
import code.repository.CottageOwnerRepository;
import code.service.CottageOwnerService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CottageOwnerServiceImpl implements CottageOwnerService {
    private CottageOwnerRepository cottageOwnerRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    @Autowired
    public CottageOwnerServiceImpl(CottageOwnerRepository cottageOwnerRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.cottageOwnerRepository = cottageOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public CottageOwner save(RegistrationRequest registrationRequest) {
        CottageOwner co = new CottageOwner();
        Location l = new Location();
        co.setEmail(registrationRequest.getEmail());
        co.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        co.setFirstName(registrationRequest.getFirstName());
        co.setLastName(registrationRequest.getLastName());
        co.setPhoneNumber(registrationRequest.getPhoneNumber());
        l.setStreetName(registrationRequest.getAddress());
        l.setCityName(registrationRequest.getCity());
        l.setCountryName(registrationRequest.getCountry());
        l.setLatitude(0);
        l.setLongitude(0);
        co.setLocation(l);
        co.setEnabled(false);
        co.setReasonForRegistration(registrationRequest.getReasonForRegistration());

        Role role = roleService.findByName("ROLE_COTTAGE_OWNER");
        co.setRole(role);

        return this.cottageOwnerRepository.save(co);
    }

}