package code.service;

import code.dto.RegistrationRequest;
import code.model.FishingInstructor;

import java.util.List;

public interface FishingInstructorService {
    FishingInstructor findById(Integer id);
    FishingInstructor findByEmail(String email);
    List<FishingInstructor> findAll ();
    FishingInstructor save(RegistrationRequest registrationRequest);
}
