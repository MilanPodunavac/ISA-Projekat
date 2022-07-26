package code.service;

import code.dto.ProviderRegistrationRequest;
import code.model.Admin;

public interface AdminService {
    void save(ProviderRegistrationRequest providerRegistrationRequest);
}
