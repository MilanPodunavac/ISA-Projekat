package code.service;

import code.dto.RegistrationRequest;
import code.model.CottageOwner;

import java.util.List;

public interface CottageOwnerService {
    CottageOwner findById(Integer id);
    CottageOwner findByEmail(String email);
    List<CottageOwner> findAll ();
    CottageOwner save(RegistrationRequest registrationRequest);
}
