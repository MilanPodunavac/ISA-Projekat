package code.service.impl;

import code.dto.RegistrationRequest;
import code.model.*;
import code.repository.BoatOwnerRepository;
import code.service.BoatOwnerService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BoatOwnerServiceImpl implements BoatOwnerService {

    @Autowired
    private BoatOwnerRepository boatOwnerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public BoatOwner findByEmail(String email) throws UsernameNotFoundException {
        return boatOwnerRepository.findByEmail(email);
    }

    public BoatOwner findById(Integer id) {
        return boatOwnerRepository.findById(id).orElseGet(null);
    }

    public List<BoatOwner> findAll() {
        return boatOwnerRepository.findAll();
    }

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

        Set<Role> roles = new HashSet<>(roleService.findByName("ROLE_BOAT_OWNER"));
        bo.setRoles(roles);

        return this.boatOwnerRepository.save(bo);
    }

}
