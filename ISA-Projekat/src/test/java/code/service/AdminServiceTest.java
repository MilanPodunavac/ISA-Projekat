package code.service;

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

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    public void changePersonalData() {
        Admin adminFromDatabase = new Admin();
        setAdminFromDatabaseFields(adminFromDatabase);

        Admin admin = new Admin();
        setNewPersonalData(admin);

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

        verify(adminRepositoryMock, times(1)).getById(admin.getId());
        verify(adminRepositoryMock, times(1)).save(adminFromDatabase);
        verifyNoMoreInteractions(adminRepositoryMock);
    }

    private void setAdminFromDatabaseFields(Admin admin) {
        admin.setId(DB_USER_ID);
        admin.setFirstName(DB_FIRST_NAME);
        admin.setLastName(DB_LAST_NAME);
        admin.setPhoneNumber(DB_PHONE_NUMBER);
        admin.setPassword(DB_PASSWORD_ENCODED);
        admin.setEmail(DB_EMAIL);
        admin.setEnabled(true);
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
    }

    private void setNewPersonalData(Admin admin) {
        admin.setId(DB_USER_ID);
        admin.setFirstName(NEW_FIRST_NAME);
        admin.setLastName(NEW_LAST_NAME);
        admin.setPhoneNumber(NEW_PHONE_NUMBER);
        Location l = new Location();
        l.setCountryName(NEW_COUNTRY);
        l.setCityName(NEW_CITY);
        l.setStreetName(NEW_ADDRESS);
        admin.setLocation(l);
    }
}
