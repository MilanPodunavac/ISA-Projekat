package code.service.impl;

import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.model.cottage.Cottage;
import code.model.cottage.CottageOwner;
import code.model.report.YearlyProfitReport;
import code.repository.CottageOwnerRepository;
import code.repository.IncomeRecordRepository;
import code.repository.LoyaltyProgramProviderRepository;
import code.service.CottageOwnerService;
import code.service.RoleService;
import code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CottageOwnerServiceImpl implements CottageOwnerService {
    private final CottageOwnerRepository cottageOwnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;
    private final LoyaltyProgramProviderRepository loyaltyProgramProviderRepository;
    private final IncomeRecordRepository _incomeRecordRepository;

    @Autowired
    public CottageOwnerServiceImpl(UserService userService, CottageOwnerRepository cottageOwnerRepository, PasswordEncoder passwordEncoder, RoleService roleService, LoyaltyProgramProviderRepository loyaltyProgramProviderRepository, IncomeRecordRepository incomeRecordRepository) {
        this.userService = userService;
        this.cottageOwnerRepository = cottageOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.loyaltyProgramProviderRepository = loyaltyProgramProviderRepository;
        _incomeRecordRepository = incomeRecordRepository;
    }

    @Override
    public void save(CottageOwner cottageOwner) throws EmailTakenException {
        userService.throwExceptionIfEmailExists(cottageOwner.getEmail());
        saveRegistrationRequest(cottageOwner);
    }

    @Override
    public List<Cottage> getCottageOwnerCottages() throws UnauthorizedAccessException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CottageOwner owner;
        try{
            owner = (CottageOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a cottage owner");
        }
        if(owner == null) throw new UserNotFoundException("Cottage owner not found");
        return new ArrayList<>(owner.getCottage());
    }

    @Override
    public YearlyProfitReport calculateYearlyProfitReport() throws UnauthorizedAccessException, UserNotFoundException {
        Authentication auth;
        try{
            auth = SecurityContextHolder.getContext().getAuthentication();
        }catch(Exception ex){
            throw new UnauthorizedAccessException("Unauthorized");
        }
        CottageOwner owner;
        try{
            owner = (CottageOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a cottage owner");
        }
        if(owner == null) throw new UserNotFoundException("Cottage owner not found");
        YearlyProfitReport report = new YearlyProfitReport();
        report.initiate();
        List<IncomeRecord> records = _incomeRecordRepository.findByReservationProviderId(owner.getId());
        for(IncomeRecord record : records){
            report.addIncome(record);
        }
        return report;
    }

    private void saveRegistrationRequest(CottageOwner cottageOwner) {
        cottageOwner.setPassword(passwordEncoder.encode(cottageOwner.getPassword()));
        cottageOwner.setEnabled(false);
        cottageOwner.setLoyaltyPoints(0);
        cottageOwner.setCategory(loyaltyProgramProviderRepository.getById(1));

        Role role = roleService.findByName("ROLE_COTTAGE_OWNER");
        cottageOwner.setRole(role);

        cottageOwnerRepository.save(cottageOwner);
    }
}