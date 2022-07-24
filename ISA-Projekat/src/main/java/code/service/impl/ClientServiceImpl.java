package code.service.impl;

import code.dto.RegistrationRequest;
import code.model.*;
import code.repository.ClientRepository;
import code.service.ClientService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public Client save(RegistrationRequest registrationRequest) {
        Client c = new Client();
        Location l = new Location();
        c.setEmail(registrationRequest.getEmail());
        c.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        c.setFirstName(registrationRequest.getFirstName());
        c.setLastName(registrationRequest.getLastName());
        c.setPhoneNumber(registrationRequest.getPhoneNumber());
        l.setStreetName(registrationRequest.getAddress());
        l.setCityName(registrationRequest.getCity());
        l.setCountryName(registrationRequest.getCountry());
        l.setLatitude(0);
        l.setLongitude(0);
        c.setLocation(l);
        c.setEnabled(false);
        c.setPenaltyPoints(0);
        c.setBanned(false);

        Role role = roleService.findByName("ROLE_CLIENT");
        c.setRole(role);

        return this.clientRepository.save(c);
    }

}

