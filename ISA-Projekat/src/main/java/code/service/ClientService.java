package code.service;

import code.dto.RegistrationRequest;
import code.model.Client;

public interface ClientService {
    Client save(RegistrationRequest registrationRequest);
}
