package code.service;

import code.exceptions.entities.EntityNotFoundException;
import code.model.LoyaltyProgramProvider;

public interface LoyaltyProgramService {
    LoyaltyProgramProvider getOneHigherLoyaltyCategory(Integer id) throws EntityNotFoundException;
}
