package code.service;

import code.dto.provider_registration.ProviderRegistrationRequest;
import code.model.Client;

public interface ClientService {
    Client save(ProviderRegistrationRequest providerRegistrationRequest);
}
