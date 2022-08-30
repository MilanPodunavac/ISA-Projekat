package code.service.impl;

import code.exceptions.entities.EntityNotFoundException;
import code.model.LoyaltyProgramProvider;
import code.repository.*;
import code.service.LoyaltyProgramService;
import code.service.UserService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {
    private final LoyaltyProgramProviderRepository _loyaltyProgramProviderRepository;

    public LoyaltyProgramServiceImpl(LoyaltyProgramProviderRepository loyaltyProgramProviderRepository) {
        this._loyaltyProgramProviderRepository = loyaltyProgramProviderRepository;
    }

    @Transactional
    @Override
    public LoyaltyProgramProvider getOneHigherLoyaltyCategory(Integer id) throws EntityNotFoundException {
        throwExceptionIfLoyaltyProgramProviderDoesntExist(id);

        if (id == 3) {
            return _loyaltyProgramProviderRepository.getById(id);
        } else {
            return _loyaltyProgramProviderRepository.getById(id + 1);
        }
    }

    private void throwExceptionIfLoyaltyProgramProviderDoesntExist(Integer id) throws EntityNotFoundException {
        Optional<LoyaltyProgramProvider> loyaltyProgramProvider = _loyaltyProgramProviderRepository.findById(id);
        if (!loyaltyProgramProvider.isPresent()) {
            throw new EntityNotFoundException("Loyalty program provider category doesn't exist");
        }
    }
}
