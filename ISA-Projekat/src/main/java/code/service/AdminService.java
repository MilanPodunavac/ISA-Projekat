package code.service;

import code.dto.RegistrationRequest;
import code.model.Admin;

import java.util.List;

public interface AdminService {
    Admin findById(Integer id);
    Admin findByEmail(String email);
    List<Admin> findAll ();
    Admin save(RegistrationRequest registrationRequest);
}
