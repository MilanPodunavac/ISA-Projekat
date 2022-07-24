package code.service.impl;

import code.dto.RegistrationRequest;
import code.model.Admin;
import code.model.Location;
import code.model.Role;
import code.repository.AdminRepository;
import code.service.AdminService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public Admin save(RegistrationRequest registrationRequest) {
        Admin a = new Admin();
        Location l = new Location();
        a.setEmail(registrationRequest.getEmail());
        a.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        a.setFirstName(registrationRequest.getFirstName());
        a.setLastName(registrationRequest.getLastName());
        a.setPhoneNumber(registrationRequest.getPhoneNumber());
        l.setStreetName(registrationRequest.getAddress());
        l.setCityName(registrationRequest.getCity());
        l.setCountryName(registrationRequest.getCountry());
        l.setLatitude(0);
        l.setLongitude(0);
        a.setLocation(l);
        a.setEnabled(false);
        a.setMainAdmin(false);

        Role role = roleService.findByName("ROLE_ADMIN");
        a.setRole(role);

        return this.adminRepository.save(a);
    }

}
