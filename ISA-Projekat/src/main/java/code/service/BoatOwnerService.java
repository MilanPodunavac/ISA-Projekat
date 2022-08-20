package code.service;

import code.exceptions.provider_registration.EmailTakenException;
import code.model.boat.BoatOwner;

public interface BoatOwnerService {
    void save(BoatOwner boatOwner) throws EmailTakenException;
}
