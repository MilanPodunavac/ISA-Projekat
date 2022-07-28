package code.service;

import code.exceptions.registration.EmailTakenException;
import code.model.BoatOwner;

public interface BoatOwnerService {
    void save(BoatOwner boatOwner) throws EmailTakenException;
}
