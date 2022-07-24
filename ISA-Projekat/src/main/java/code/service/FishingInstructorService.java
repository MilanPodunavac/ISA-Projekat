package code.service;

import code.dto.RegistrationRequest;
import code.model.FishingInstructor;

public interface FishingInstructorService {
    FishingInstructor save(RegistrationRequest registrationRequest);
}
