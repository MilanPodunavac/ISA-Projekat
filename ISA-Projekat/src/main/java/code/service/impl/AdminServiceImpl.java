package code.service.impl;

import code.exceptions.admin.ChangedPasswordException;
import code.exceptions.admin.ModifyAnotherUserDataException;
import code.exceptions.admin.NonMainAdminRegisterOtherAdminException;
import code.exceptions.admin.NotChangedPasswordException;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.Admin;
import code.model.Role;
import code.model.User;
import code.repository.AdminRepository;
import code.service.AdminService;
import code.service.RoleService;
import code.service.UserService;
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

    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder, RoleService roleService, UserService userService) {
        this._adminRepository = adminRepository;
        this._passwordEncoder = passwordEncoder;
        this._roleService = roleService;
        this._userService = userService;
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
    public Admin changePersonalData(Admin admin) throws UserNotFoundException, ModifyAnotherUserDataException, NotChangedPasswordException {
        _userService.throwExceptionIfUserDontExist(admin.getId());
        _userService.throwExceptionIfModifyAnotherUserData(admin.getId());
        throwExceptionIfAdminDidntChangePassword();
        return changePersonalDataFields(admin);
    }

    private void throwExceptionIfAdminDidntChangePassword() throws NotChangedPasswordException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Admin admin = (Admin) _userService.findById(user.getId());
        if (!admin.isPasswordChanged()) {
            throw new NotChangedPasswordException("Password not changed!");
        }
    }

    private Admin changePersonalDataFields(Admin admin) {
        Admin adminFromDatabase = _adminRepository.getById(admin.getId());
        adminFromDatabase.setFirstName(admin.getFirstName());
        adminFromDatabase.setLastName(admin.getLastName());
        adminFromDatabase.setPhoneNumber(admin.getPhoneNumber());
        adminFromDatabase.getLocation().setCountryName(admin.getLocation().getCountryName());
        adminFromDatabase.getLocation().setCityName(admin.getLocation().getCityName());
        adminFromDatabase.getLocation().setStreetName(admin.getLocation().getStreetName());
        return _adminRepository.save(adminFromDatabase);
    }

    @Transactional
    @Override
    public void changePassword(Admin admin) throws ModifyAnotherUserDataException, UserNotFoundException, ChangedPasswordException {
        _userService.throwExceptionIfUserDontExist(admin.getId());
        _userService.throwExceptionIfModifyAnotherUserData(admin.getId());
        throwExceptionIfAdminChangedPassword();
        changePasswordField(admin);
    }

    private void throwExceptionIfAdminChangedPassword() throws ChangedPasswordException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Admin admin = (Admin) _userService.findById(user.getId());
        if (admin.isPasswordChanged()) {
            throw new ChangedPasswordException("Password already changed!");
        }
    }

    private void changePasswordField(Admin admin) {
        Admin adminFromDatabase = _adminRepository.getById(admin.getId());
        adminFromDatabase.setPassword(_passwordEncoder.encode(admin.getPassword()));
        adminFromDatabase.setPasswordChanged(true);
        _adminRepository.save(adminFromDatabase);
    }
}
