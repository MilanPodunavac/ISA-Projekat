package code.service.impl;

import code.exceptions.admin.*;
import code.exceptions.provider_registration.EmailTakenException;
import code.model.*;
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
}
