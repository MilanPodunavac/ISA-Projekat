package code.service.impl;

import code.dto.provider_registration.ProviderRegistrationRequest;
import code.model.*;
import code.repository.ClientRepository;
import code.service.ClientService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public Client save(ProviderRegistrationRequest providerRegistrationRequest) {
        Client c = new Client();
        Location l = new Location();
        c.setEmail(providerRegistrationRequest.getEmail());
        c.setPassword(passwordEncoder.encode(providerRegistrationRequest.getPassword()));

        c.setFirstName(providerRegistrationRequest.getFirstName());
        c.setLastName(providerRegistrationRequest.getLastName());
        c.setPhoneNumber(providerRegistrationRequest.getPhoneNumber());
        l.setStreetName(providerRegistrationRequest.getAddress());
        l.setCityName(providerRegistrationRequest.getCity());
        l.setCountryName(providerRegistrationRequest.getCountry());
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

