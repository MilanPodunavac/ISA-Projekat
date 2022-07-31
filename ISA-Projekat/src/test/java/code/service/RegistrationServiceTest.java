package code.service;

import code.exceptions.registration.EmailTakenException;
import code.exceptions.registration.UserAccountActivatedException;
import code.exceptions.registration.UserNotFoundException;
import code.model.FishingInstructor;
import code.model.Location;
import code.model.Role;
import code.model.User;
import code.repository.FishingInstructorRepository;
import code.repository.UserRepository;
import code.service.impl.FishingInstructorServiceImpl;
import code.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.util.Optional;

import static code.constants.RegistrationConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationServiceTest {
    @Mock
    private FishingInstructorRepository fishingInstructorRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleServiceMock;

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private FishingInstructorServiceImpl fishingInstructorService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void saveRegistrationRequest() throws EmailTakenException {
        FishingInstructor fishingInstructor = new FishingInstructor();
        setFishingInstructorSaveRegistrationRequest(fishingInstructor);
        fishingInstructor.setEmail(NEW_EMAIL);

        when(fishingInstructorRepositoryMock.save(fishingInstructor)).thenReturn(fishingInstructor);
        when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn(NEW_PASSWORD_ENCODED);
        when(roleServiceMock.findByName(DB_ROLE_NAME)).thenReturn(new Role(DB_ROLE_ID, DB_ROLE_NAME));

        FishingInstructor dbFishingInstructor = fishingInstructorService.save(fishingInstructor);

        assertThat(dbFishingInstructor).isNotNull();
        assertThat(dbFishingInstructor.getFirstName()).isEqualTo(NEW_FIRST_NAME);
        assertThat(dbFishingInstructor.getLastName()).isEqualTo(NEW_LAST_NAME);
        assertThat(dbFishingInstructor.getBiography()).isEqualTo(NEW_BIOGRAPHY);
        assertThat(dbFishingInstructor.getEmail()).isEqualTo(NEW_EMAIL);
        assertThat(dbFishingInstructor.getReasonForRegistration()).isEqualTo(NEW_REASON_FOR_REGISTRATION);
        assertThat(dbFishingInstructor.getPhoneNumber()).isEqualTo(NEW_PHONE_NUMBER);
        assertThat(dbFishingInstructor.getLocation().getCityName()).isEqualTo(NEW_CITY);
        assertThat(dbFishingInstructor.getLocation().getCountryName()).isEqualTo(NEW_COUNTRY);
        assertThat(dbFishingInstructor.getLocation().getStreetName()).isEqualTo(NEW_ADDRESS);
        assertThat(dbFishingInstructor.getRole().getName()).isEqualTo("ROLE_FISHING_INSTRUCTOR");
        assertThat(dbFishingInstructor.isEnabled()).isEqualTo(false);
        assertThat(dbFishingInstructor.getPassword()).isEqualTo(NEW_PASSWORD_ENCODED);

        verify(userServiceMock, times(1)).throwExceptionIfEmailExists(fishingInstructor.getEmail());
        verify(fishingInstructorRepositoryMock, times(1)).save(fishingInstructor);
        verify(passwordEncoder, times(1)).encode(NEW_PASSWORD);
        verify(roleServiceMock, times(1)).findByName(DB_ROLE_NAME);
        verifyNoMoreInteractions(userServiceMock);
        verifyNoMoreInteractions(fishingInstructorRepositoryMock);
        verifyNoMoreInteractions(passwordEncoder);
        verifyNoMoreInteractions(roleServiceMock);
    }

    @Test(expected = EmailTakenException.class)
    public void saveRegistrationRequestWithExistingEmail() throws EmailTakenException {
        FishingInstructor fishingInstructor = new FishingInstructor();
        setFishingInstructorSaveRegistrationRequest(fishingInstructor);
        fishingInstructor.setEmail(EXISTING_EMAIL);

        doThrow(EmailTakenException.class).when(userServiceMock).throwExceptionIfEmailExists(fishingInstructor.getEmail());

        fishingInstructorService.save(fishingInstructor);

        verify(userServiceMock, times(1)).throwExceptionIfEmailExists(fishingInstructor.getEmail());
        verifyNoMoreInteractions(userServiceMock);
    }

    @Test
    public void acceptRegistrationRequest() throws UserNotFoundException, UserAccountActivatedException, MessagingException {
        FishingInstructor fishingInstructor = new FishingInstructor();
        setFishingInstructorAcceptRegistrationRequest(fishingInstructor);
        fishingInstructor.setEnabled(false);

        when(userRepositoryMock.findById(NEW_USER_ID)).thenReturn(Optional.of(fishingInstructor));
        when(userRepositoryMock.save(fishingInstructor)).thenReturn(fishingInstructor);

        userService.acceptRegistrationRequest(NEW_USER_ID);
        User user = userService.findById(NEW_USER_ID);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(NEW_USER_ID);
        assertThat(user.isEnabled()).isEqualTo(true);

        verify(userRepositoryMock, times(4)).findById(NEW_USER_ID);
        verify(userRepositoryMock, times(1)).save(fishingInstructor);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test(expected = UserNotFoundException.class)
    public void acceptRegistrationRequestForUserNotFound() throws UserNotFoundException, UserAccountActivatedException {
        when(userRepositoryMock.findById(NEW_USER_ID)).thenReturn(Optional.empty());

        userService.acceptRegistrationRequest(NEW_USER_ID);

        verify(userRepositoryMock, times(1)).findById(NEW_USER_ID);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test(expected = UserAccountActivatedException.class)
    public void acceptRegistrationRequestForActivatedUser() throws UserNotFoundException, UserAccountActivatedException {
        FishingInstructor fishingInstructor = new FishingInstructor();
        setFishingInstructorAcceptRegistrationRequest(fishingInstructor);
        fishingInstructor.setEnabled(true);

        when(userRepositoryMock.findById(NEW_USER_ID)).thenReturn(Optional.of(fishingInstructor));

        userService.acceptRegistrationRequest(NEW_USER_ID);

        verify(userRepositoryMock, times(2)).findById(NEW_USER_ID);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    private void setFishingInstructorSaveRegistrationRequest(FishingInstructor fishingInstructor) {
        fishingInstructor.setFirstName(NEW_FIRST_NAME);
        fishingInstructor.setLastName(NEW_LAST_NAME);
        fishingInstructor.setBiography(NEW_BIOGRAPHY);
        fishingInstructor.setReasonForRegistration(NEW_REASON_FOR_REGISTRATION);
        fishingInstructor.setPhoneNumber(NEW_PHONE_NUMBER);
        fishingInstructor.setPassword(NEW_PASSWORD);
        Location l = new Location();
        l.setCountryName(NEW_COUNTRY);
        l.setCityName(NEW_CITY);
        l.setStreetName(NEW_ADDRESS);
        fishingInstructor.setLocation(l);
    }

    private void setFishingInstructorAcceptRegistrationRequest(FishingInstructor fishingInstructor) {
        setFishingInstructorSaveRegistrationRequest(fishingInstructor);
        fishingInstructor.setId(NEW_USER_ID);
        fishingInstructor.setEmail(NEW_EMAIL);
        fishingInstructor.setPassword(NEW_PASSWORD_ENCODED);
        fishingInstructor.setRole(new Role(DB_ROLE_ID, DB_ROLE_NAME));
    }
}
