package code.service;

import code.dto.RegistrationRequest;
import code.model.Admin;

public interface AdminService {
    Admin save(RegistrationRequest registrationRequest);
}
