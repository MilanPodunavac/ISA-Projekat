package code.service;

import code.exceptions.registration.EmailTakenException;
import code.model.CottageOwner;

public interface CottageOwnerService {
    void save(CottageOwner cottageOwner) throws EmailTakenException;
}
