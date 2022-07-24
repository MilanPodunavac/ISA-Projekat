package code.service.impl;

import code.dto.RegistrationRequest;
import code.model.*;
import code.repository.CottageOwnerRepository;
import code.service.CottageOwnerService;
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
public class CottageOwnerServiceImpl implements CottageOwnerService {

    @Autowired
    private CottageOwnerRepository cottageOwnerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public CottageOwner findByEmail(String email) throws UsernameNotFoundException {
        return cottageOwnerRepository.findByEmail(email);
    }

    public CottageOwner findById(Integer id) {
        return cottageOwnerRepository.findById(id).orElseGet(null);
    }

    public List<CottageOwner> findAll() {
        return cottageOwnerRepository.findAll();
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

        Set<Role> roles = new HashSet<>(roleService.findByName("ROLE_COTTAGE_OWNER"));
        co.setRoles(roles);

        return this.cottageOwnerRepository.save(co);
    }

}