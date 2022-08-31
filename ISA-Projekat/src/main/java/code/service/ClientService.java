package code.service;

import code.dto.provider_registration.ProviderRegistrationRequest;
import code.exceptions.entities.EntityNotFoundException;
import code.model.Client;
import code.model.boat.Boat;

import java.util.List;

public interface ClientService {
    Client save(ProviderRegistrationRequest providerRegistrationRequest);
    List<Client> getAllClients();
    Client getClient(Integer id) throws EntityNotFoundException;
    Client getLoggedInClient();
}
