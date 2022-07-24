package code.service;

import code.dto.RegistrationRequest;
import code.model.Client;

import java.util.List;

public interface ClientService {
    Client findById(Integer id);
    Client findByEmail(String email);
    List<Client> findAll ();
    Client save(RegistrationRequest registrationRequest);
}
