package code.service;

import code.exceptions.provider_registration.EmailTakenException;
import code.model.FishingInstructor;

public interface FishingInstructorService {
    FishingInstructor save(FishingInstructor fishingInstructor) throws EmailTakenException;
}
