package code.service;

import code.exceptions.provider_registration.EmailTakenException;
import code.model.cottage.CottageOwner;

public interface CottageOwnerService {
    void save(CottageOwner cottageOwner) throws EmailTakenException;
}
