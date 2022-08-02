package code.service;

import code.exceptions.admin.ModifyAnotherUserDataException;
import code.exceptions.admin.NonMainAdminRegisterOtherAdminException;
import code.exceptions.admin.NotChangedPasswordException;
import code.exceptions.registration.EmailTakenException;
import code.exceptions.registration.UserNotFoundException;
import code.model.Admin;
import code.model.Location;
import code.model.Role;
import code.repository.AdminRepository;
import code.service.impl.AdminServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static code.constants.AdminConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {
    @Mock
    private AdminRepository adminRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleServiceMock;

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    public void changePersonalData() throws UserNotFoundException, ModifyAnotherUserDataException, NotChangedPasswordException {
        Admin adminFromDatabase = setAdminFromDatabaseFields();
        Admin admin = setNewPersonalData();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(adminFromDatabase);
        when(userServiceMock.findById(adminFromDatabase.getId())).thenReturn(adminFromDatabase);
        when(adminRepositoryMock.getById(admin.getId())).thenReturn(adminFromDatabase);
        when(adminRepositoryMock.save(adminFromDatabase)).thenReturn(adminFromDatabase);

        Admin changedAdmin = adminService.changePersonalData(admin);

        assertThat(changedAdmin).isNotNull();
        assertThat(changedAdmin.getFirstName()).isEqualTo(NEW_FIRST_NAME);
        assertThat(changedAdmin.getLastName()).isEqualTo(NEW_LAST_NAME);
        assertThat(changedAdmin.getPhoneNumber()).isEqualTo(NEW_PHONE_NUMBER);
        assertThat(changedAdmin.getLocation().getCityName()).isEqualTo(NEW_CITY);
        assertThat(changedAdmin.getLocation().getCountryName()).isEqualTo(NEW_COUNTRY);
        assertThat(changedAdmin.getLocation().getStreetName()).isEqualTo(NEW_ADDRESS);

        verify(userServiceMock, times(1)).findById(adminFromDatabase.getId());
        verify(userServiceMock, times(1)).throwExceptionIfUserDontExist(admin.getId());
        verify(userServiceMock, times(1)).throwExceptionIfModifyAnotherUserData(admin.getId());
        verify(adminRepositoryMock, times(1)).getById(admin.getId());
        verify(adminRepositoryMock, times(1)).save(adminFromDatabase);
        verifyNoMoreInteractions(userServiceMock);
        verifyNoMoreInteractions(adminRepositoryMock);
    }

    @Test
    public void register() throws NonMainAdminRegisterOtherAdminException, EmailTakenException {
        Admin loggedInAdmin = setAdminFromDatabaseFields();
        loggedInAdmin.setMainAdmin(true);
        Admin admin = setAdminRegistrationData();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(loggedInAdmin);
        when(userServiceMock.findById(loggedInAdmin.getId())).thenReturn(loggedInAdmin);
        when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn(DB_PASSWORD_ENCODED);
        when(roleServiceMock.findByName(DB_ROLE_NAME)).thenReturn(new Role(DB_ROLE_ID, DB_ROLE_NAME));
        when(adminRepositoryMock.save(admin)).thenReturn(admin);

        Admin dbAdmin = adminService.save(admin);

        assertThat(dbAdmin).isNotNull();
        assertThat(dbAdmin.getFirstName()).isEqualTo(NEW_FIRST_NAME);
        assertThat(dbAdmin.getLastName()).isEqualTo(NEW_LAST_NAME);
        assertThat(dbAdmin.getEmail()).isEqualTo(NEW_EMAIL);
        assertThat(dbAdmin.getPhoneNumber()).isEqualTo(NEW_PHONE_NUMBER);
        assertThat(dbAdmin.getLocation().getCityName()).isEqualTo(NEW_CITY);
        assertThat(dbAdmin.getLocation().getCountryName()).isEqualTo(NEW_COUNTRY);
        assertThat(dbAdmin.getLocation().getStreetName()).isEqualTo(NEW_ADDRESS);
        assertThat(dbAdmin.getRole().getName()).isEqualTo("ROLE_ADMIN");
        assertThat(dbAdmin.isEnabled()).isEqualTo(true);
        assertThat(dbAdmin.isMainAdmin()).isEqualTo(false);
        assertThat(dbAdmin.isPasswordChanged()).isEqualTo(false);
        assertThat(dbAdmin.getPassword()).isEqualTo(DB_PASSWORD_ENCODED);

        verify(userServiceMock, times(1)).findById(loggedInAdmin.getId());
        verify(userServiceMock, times(1)).throwExceptionIfEmailExists(admin.getEmail());
        verify(adminRepositoryMock, times(1)).save(admin);
        verify(passwordEncoder, times(1)).encode(NEW_PASSWORD);
        verify(roleServiceMock, times(1)).findByName(DB_ROLE_NAME);
        verifyNoMoreInteractions(userServiceMock);
        verifyNoMoreInteractions(adminRepositoryMock);
        verifyNoMoreInteractions(passwordEncoder);
        verifyNoMoreInteractions(roleServiceMock);
    }

    @Test(expected = NonMainAdminRegisterOtherAdminException.class)
    public void registerWithNonMainAdminLoggedIn() throws NonMainAdminRegisterOtherAdminException, EmailTakenException {
        Admin loggedInAdmin = setAdminFromDatabaseFields();
        loggedInAdmin.setMainAdmin(false);
        Admin admin = setAdminRegistrationData();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(loggedInAdmin);
        when(userServiceMock.findById(loggedInAdmin.getId())).thenReturn(loggedInAdmin);

        adminService.save(admin);

        verify(userServiceMock, times(1)).findById(loggedInAdmin.getId());
        verifyNoMoreInteractions(userServiceMock);
    }

    private Admin setAdminFromDatabaseFields() {
        Admin admin = new Admin();
        admin.setId(DB_USER_ID);
        admin.setFirstName(DB_FIRST_NAME);
        admin.setLastName(DB_LAST_NAME);
        admin.setPhoneNumber(DB_PHONE_NUMBER);
        admin.setPassword(DB_PASSWORD_ENCODED);
        admin.setEmail(DB_EMAIL);
        admin.setEnabled(true);
        admin.setPasswordChanged(true);
        admin.setMainAdmin(true);
        Location l = new Location();
        l.setId(DB_LOCATION_ID);
        l.setCountryName(DB_COUNTRY);
        l.setCityName(DB_CITY);
        l.setStreetName(DB_ADDRESS);
        admin.setLocation(l);
        Role r = new Role();
        r.setId(DB_ROLE_ID);
        r.setName(DB_ROLE_NAME);
        admin.setRole(r);
        return admin;
    }

    private Admin setNewPersonalData() {
        Admin admin = new Admin();
        admin.setId(DB_USER_ID);
        admin.setFirstName(NEW_FIRST_NAME);
        admin.setLastName(NEW_LAST_NAME);
        admin.setPhoneNumber(NEW_PHONE_NUMBER);
        Location l = new Location();
        l.setCountryName(NEW_COUNTRY);
        l.setCityName(NEW_CITY);
        l.setStreetName(NEW_ADDRESS);
        admin.setLocation(l);
        return admin;
    }

    private Admin setAdminRegistrationData() {
        Admin admin = new Admin();
        admin.setFirstName(NEW_FIRST_NAME);
        admin.setLastName(NEW_LAST_NAME);
        admin.setPhoneNumber(NEW_PHONE_NUMBER);
        admin.setEmail(NEW_EMAIL);
        admin.setPassword(NEW_PASSWORD);
        Location l = new Location();
        l.setCountryName(NEW_COUNTRY);
        l.setCityName(NEW_CITY);
        l.setStreetName(NEW_ADDRESS);
        admin.setLocation(l);
        return admin;
    }
}
