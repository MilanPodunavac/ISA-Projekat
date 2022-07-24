package code.service.impl;

import code.dto.RegistrationRequest;
import code.model.*;
import code.repository.AdminRepository;
import code.repository.ClientRepository;
import code.service.AdminService;
import code.service.ClientService;
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
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public Client findByEmail(String email) throws UsernameNotFoundException {
        return clientRepository.findByEmail(email);
    }

    public Client findById(Integer id) {
        return clientRepository.findById(id).orElseGet(null);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
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

        Set<Role> roles = new HashSet<>(roleService.findByName("ROLE_CLIENT"));
        c.setRoles(roles);

        return this.clientRepository.save(c);
    }

}

