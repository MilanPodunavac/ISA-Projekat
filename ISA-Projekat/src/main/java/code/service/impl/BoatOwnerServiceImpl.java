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

    @Autowired
    private BoatOwnerRepository boatOwnerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public BoatOwner save(RegistrationRequest registrationRequest) {
        BoatOwner bo = new BoatOwner();
        Location l = new Location();
        bo.setEmail(registrationRequest.getEmail());
        bo.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        bo.setFirstName(registrationRequest.getFirstName());
        bo.setLastName(registrationRequest.getLastName());
        bo.setPhoneNumber(registrationRequest.getPhoneNumber());
        l.setStreetName(registrationRequest.getAddress());
        l.setCityName(registrationRequest.getCity());
        l.setCountryName(registrationRequest.getCountry());
        l.setLatitude(0);
        l.setLongitude(0);
        bo.setLocation(l);
        bo.setEnabled(false);
        bo.setReasonForRegistration(registrationRequest.getReasonForRegistration());

        Role role = roleService.findByName("ROLE_BOAT_OWNER");
        bo.setRole(role);

        return this.boatOwnerRepository.save(bo);
    }

}
