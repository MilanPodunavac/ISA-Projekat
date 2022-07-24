package code.service;

import code.dto.RegistrationRequest;
import code.model.BoatOwner;

import java.util.List;

public interface BoatOwnerService {
    BoatOwner findById(Integer id);
    BoatOwner findByEmail(String email);
    List<BoatOwner> findAll ();
    BoatOwner save(RegistrationRequest registrationRequest);
}
