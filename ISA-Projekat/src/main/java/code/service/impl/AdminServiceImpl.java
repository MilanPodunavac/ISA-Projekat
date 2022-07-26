package code.service.impl;

import code.dto.ProviderRegistrationRequest;
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
    private final AdminRepository _adminRepository;
    private final PasswordEncoder _passwordEncoder;
    private final RoleService _roleService;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this._adminRepository = adminRepository;
        this._passwordEncoder = passwordEncoder;
        this._roleService = roleService;
    }

    @Override
    public void save(ProviderRegistrationRequest providerRegistrationRequest) {
        Admin a = new Admin();
        Location l = new Location();
        a.setEmail(providerRegistrationRequest.getEmail());
        a.setPassword(_passwordEncoder.encode(providerRegistrationRequest.getPassword()));

        a.setFirstName(providerRegistrationRequest.getFirstName());
        a.setLastName(providerRegistrationRequest.getLastName());
        a.setPhoneNumber(providerRegistrationRequest.getPhoneNumber());
        l.setStreetName(providerRegistrationRequest.getAddress());
        l.setCityName(providerRegistrationRequest.getCity());
        l.setCountryName(providerRegistrationRequest.getCountry());
        l.setLatitude(0);
        l.setLongitude(0);
        a.setLocation(l);
        a.setEnabled(false);
        a.setMainAdmin(false);

        Role role = _roleService.findByName("ROLE_ADMIN");
        a.setRole(role);

        this._adminRepository.save(a);
    }

}
