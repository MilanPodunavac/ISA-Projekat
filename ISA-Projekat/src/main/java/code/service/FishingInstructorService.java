package code.service;

import code.dto.RegistrationRequest;
import code.model.FishingInstructor;

public interface FishingInstructorService {
    boolean save(RegistrationRequest registrationRequest);
}
