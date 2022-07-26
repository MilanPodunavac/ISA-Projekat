package code.service;

import code.dto.ProviderRegistrationRequest;
import code.model.Client;

public interface ClientService {
    Client save(ProviderRegistrationRequest providerRegistrationRequest);
}
