package code.service.impl;

import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.model.boat.Boat;
import code.model.boat.BoatOwner;
import code.model.cottage.CottageOwner;
import code.model.report.YearlyProfitReport;
import code.repository.BoatOwnerRepository;
import code.repository.IncomeRecordRepository;
import code.repository.LoyaltyProgramProviderRepository;
import code.service.BoatOwnerService;
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
public class BoatOwnerServiceImpl implements BoatOwnerService {
    private final BoatOwnerRepository boatOwnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;
    private final LoyaltyProgramProviderRepository loyaltyProgramProviderRepository;
    private final IncomeRecordRepository _incomeRecordRepository;

    @Autowired
    public BoatOwnerServiceImpl(UserService userService, BoatOwnerRepository boatOwnerRepository, PasswordEncoder passwordEncoder, RoleService roleService, LoyaltyProgramProviderRepository loyaltyProgramProviderRepository, IncomeRecordRepository incomeRecordRepository) {
        this.userService = userService;
        this.boatOwnerRepository = boatOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.loyaltyProgramProviderRepository = loyaltyProgramProviderRepository;
        _incomeRecordRepository = incomeRecordRepository;
    }

    @Override
    public void save(BoatOwner boatOwner) throws EmailTakenException {
        userService.throwExceptionIfEmailExists(boatOwner.getEmail());
        saveRegistrationRequest(boatOwner);
    }

    @Override
    public List<Boat> getBoatOwnerBoats() throws UnauthorizedAccessException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        return new ArrayList<>(owner.getBoat());
    }

    @Override
    public YearlyProfitReport calculateYearlyProfitReport() throws UnauthorizedAccessException, UserNotFoundException {
        Authentication auth;
        try{
            auth = SecurityContextHolder.getContext().getAuthentication();
        }catch(Exception ex){
            throw new UnauthorizedAccessException("Unauthorized");
        }
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        YearlyProfitReport report = new YearlyProfitReport();
        report.initiate();
        List<IncomeRecord> records = _incomeRecordRepository.findByReservationProviderId(owner.getId());
        for(IncomeRecord record : records){
            report.addIncome(record);
        }
        return report;
    }

    private void saveRegistrationRequest(BoatOwner boatOwner) {
        boatOwner.setPassword(passwordEncoder.encode(boatOwner.getPassword()));
        boatOwner.setEnabled(false);
        boatOwner.setLoyaltyPoints(0);
        boatOwner.setCategory(loyaltyProgramProviderRepository.getById(1));

        Role role = roleService.findByName("ROLE_BOAT_OWNER");
        boatOwner.setRole(role);

        boatOwnerRepository.save(boatOwner);
    }
}
