package code.service.impl;

import code.exceptions.admin.*;
import code.exceptions.entities.EntityNotDeletableException;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.UnexpectedUserRoleException;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.model.cottage.CottageOwner;
import code.repository.AdminRepository;
import code.repository.UserRepository;
import code.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository _adminRepository;
    private final PasswordEncoder _passwordEncoder;
    private final RoleService _roleService;
    private final UserService _userService;
    private final UserRepository _userRepository;
    private final CottageService _cottageService;
    private final BoatService _boatService;

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder, RoleService roleService, UserService userService, UserRepository userRepository, CottageService cottageService, BoatService boatService) {
        this._adminRepository = adminRepository;
        this._passwordEncoder = passwordEncoder;
        this._roleService = roleService;
        this._userService = userService;
        this._userRepository = userRepository;
        this._cottageService = cottageService;
        this._boatService = boatService;
    }

    @Override
    public Admin save(Admin admin) throws NonMainAdminRegisterOtherAdminException, EmailTakenException {
        throwExceptionIfNonMainAdminRegisterOtherAdmin();
        _userService.throwExceptionIfEmailExists(admin.getEmail());
        return saveFields(admin);
    }

    private void throwExceptionIfNonMainAdminRegisterOtherAdmin() throws NonMainAdminRegisterOtherAdminException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Admin admin = (Admin) _userService.findById(user.getId());
        if (!admin.isMainAdmin()) {
            throw new NonMainAdminRegisterOtherAdminException("Only main administrator can register other administrators!");
        }
    }

    private Admin saveFields(Admin admin) {
        admin.setPassword(_passwordEncoder.encode(admin.getPassword()));
        admin.setEnabled(true);
        admin.setMainAdmin(false);
        admin.setPasswordChanged(false);

        Role role = _roleService.findByName("ROLE_ADMIN");
        admin.setRole(role);

        return _adminRepository.save(admin);
    }

    @Transactional
    @Override
    public Admin changePersonalData(Admin admin) throws NotChangedPasswordException {
        Admin loggedInAdmin = getLoggedInAdmin();
        throwExceptionIfAdminDidntChangePassword(loggedInAdmin);
        return changePersonalDataFields(loggedInAdmin, admin);
    }

    private void throwExceptionIfAdminDidntChangePassword(Admin admin) throws NotChangedPasswordException {
        if (!admin.isPasswordChanged()) {
            throw new NotChangedPasswordException("Password not changed!");
        }
    }

    private Admin getLoggedInAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return (Admin) _userService.findById(user.getId());
    }

    private Admin changePersonalDataFields(Admin loggedInAdmin, Admin admin) {
        loggedInAdmin.setFirstName(admin.getFirstName());
        loggedInAdmin.setLastName(admin.getLastName());
        loggedInAdmin.setPhoneNumber(admin.getPhoneNumber());
        loggedInAdmin.getLocation().setCountryName(admin.getLocation().getCountryName());
        loggedInAdmin.getLocation().setCityName(admin.getLocation().getCityName());
        loggedInAdmin.getLocation().setStreetName(admin.getLocation().getStreetName());
        return _adminRepository.save(loggedInAdmin);
    }

    @Transactional
    @Override
    public void changePassword(Admin admin) throws ChangedPasswordException {
        Admin loggedInAdmin = getLoggedInAdmin();
        throwExceptionIfAdminChangedPassword(loggedInAdmin);
        changePasswordField(loggedInAdmin, admin);
    }

    private void throwExceptionIfAdminChangedPassword(Admin admin) throws ChangedPasswordException {
        if (admin.isPasswordChanged()) {
            throw new ChangedPasswordException("Password already changed!");
        }
    }

    private void changePasswordField(Admin loggedInAdmin, Admin admin) {
        loggedInAdmin.setPassword(_passwordEncoder.encode(admin.getPassword()));
        loggedInAdmin.setPasswordChanged(true);
        _adminRepository.save(loggedInAdmin);
    }

    @Transactional
    @Override
    public void deleteFishingInstructor(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException {
        _userService.throwExceptionIfUserDontExist(id);
        throwExceptionIfUserRoleNotFishingInstructor(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        FishingInstructor fishingInstructor = (FishingInstructor) _userService.findById(id);
        _userService.checkIfFishingInstructorDeletable(fishingInstructor);
        _userService.unlinkReferencesFishingInstructor(fishingInstructor);
        _userRepository.delete(fishingInstructor);
    }

    private void throwExceptionIfUserRoleNotFishingInstructor(Integer id) throws UnexpectedUserRoleException {
        if (!_userService.findById(id).getRole().getName().equals("ROLE_FISHING_INSTRUCTOR")) {
            throw new UnexpectedUserRoleException("User role isn't fishing instructor!");
        }
    }

    @Transactional
    @Override
    public void deleteCottageOwner(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException {
        _userService.throwExceptionIfUserDontExist(id);
        throwExceptionIfUserRoleNotCottageOwner(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        CottageOwner cottageOwner = (CottageOwner) _userService.findById(id);
        _userService.checkIfCottageOwnerDeletable(cottageOwner);
        _userService.unlinkReferencesCottageOwner(cottageOwner);
        _userRepository.delete(cottageOwner);
    }

    private void throwExceptionIfUserRoleNotCottageOwner(Integer id) throws UnexpectedUserRoleException {
        if (!_userService.findById(id).getRole().getName().equals("ROLE_COTTAGE_OWNER")) {
            throw new UnexpectedUserRoleException("User role isn't cottage owner!");
        }
    }

    @Transactional
    @Override
    public void deleteBoatOwner(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException {
        _userService.throwExceptionIfUserDontExist(id);
        throwExceptionIfUserRoleNotBoatOwner(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        BoatOwner boatOwner = (BoatOwner) _userService.findById(id);
        _userService.checkIfBoatOwnerDeletable(boatOwner);
        _userService.unlinkReferencesBoatOwner(boatOwner);
        _userRepository.delete(boatOwner);
    }

    private void throwExceptionIfUserRoleNotBoatOwner(Integer id) throws UnexpectedUserRoleException {
        if (!_userService.findById(id).getRole().getName().equals("ROLE_BOAT_OWNER")) {
            throw new UnexpectedUserRoleException("User role isn't boat owner!");
        }
    }

    @Transactional
    @Override
    public void deleteClient(Integer id) throws NotChangedPasswordException, UserNotFoundException, UnexpectedUserRoleException, EntityNotDeletableException {
        _userService.throwExceptionIfUserDontExist(id);
        throwExceptionIfUserRoleNotClient(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        Client client = (Client) _userService.findById(id);
        _userService.checkIfClientDeletable(client);
        _userService.unlinkReferencesClient(client);
        _userRepository.delete(client);
    }

    private void throwExceptionIfUserRoleNotClient(Integer id) throws UnexpectedUserRoleException {
        if (!_userService.findById(id).getRole().getName().equals("ROLE_CLIENT")) {
            throw new UnexpectedUserRoleException("User role isn't client!");
        }
    }

    @Transactional
    @Override
    public void deleteCottage(Integer id) throws NotChangedPasswordException, EntityNotFoundException, EntityNotDeletableException {
        _cottageService.throwExceptionIfCottageDontExist(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        _cottageService.checkIfCottageDeletable(id);
        _cottageService.unlinkReferencesAndDeleteCottage(id);
    }

    @Transactional
    @Override
    public void deleteBoat(Integer id) throws NotChangedPasswordException, EntityNotFoundException, EntityNotDeletableException {
        _boatService.throwExceptionIfBoatDontExist(id);
        throwExceptionIfAdminDidntChangePassword(getLoggedInAdmin());

        _boatService.checkIfBoatDeletable(id);
        _boatService.unlinkReferencesAndDeleteBoat(id);
    }
}
