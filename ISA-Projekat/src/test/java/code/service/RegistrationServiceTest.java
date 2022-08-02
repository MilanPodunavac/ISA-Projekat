package code.service;

import code.exceptions.registration.EmailTakenException;
import code.exceptions.registration.NotProviderException;
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
        FishingInstructor fishingInstructor = setFishingInstructorSaveRegistrationRequest();
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

        verify(fishingInstructorRepositoryMock, times(1)).save(fishingInstructor);
        verify(passwordEncoder, times(1)).encode(NEW_PASSWORD);
        verify(roleServiceMock, times(1)).findByName(DB_ROLE_NAME);
        verifyNoMoreInteractions(fishingInstructorRepositoryMock);
        verifyNoMoreInteractions(passwordEncoder);
        verifyNoMoreInteractions(roleServiceMock);
    }

    @Test
    public void acceptRegistrationRequest() throws UserNotFoundException, UserAccountActivatedException, NotProviderException {
        FishingInstructor fishingInstructor = setFishingInstructorAcceptOrDeleteRegistrationRequest();
        fishingInstructor.setEnabled(false);

        when(userRepositoryMock.findById(NEW_USER_ID)).thenReturn(Optional.of(fishingInstructor));
        when(userRepositoryMock.save(fishingInstructor)).thenReturn(fishingInstructor);

        userService.acceptRegistrationRequest(NEW_USER_ID);
        User user = userService.findById(NEW_USER_ID);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(NEW_USER_ID);
        assertThat(user.isEnabled()).isEqualTo(true);

        verify(userRepositoryMock, times(5)).findById(NEW_USER_ID);
        verify(userRepositoryMock, times(1)).save(fishingInstructor);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test(expected = UserNotFoundException.class)
    public void acceptRegistrationRequestForUserNotFound() throws UserNotFoundException, UserAccountActivatedException, NotProviderException {
        when(userRepositoryMock.findById(NEW_USER_ID)).thenReturn(Optional.empty());

        userService.acceptRegistrationRequest(NEW_USER_ID);

        verify(userRepositoryMock, times(1)).findById(NEW_USER_ID);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test(expected = UserAccountActivatedException.class)
    public void acceptRegistrationRequestForActivatedUser() throws UserNotFoundException, UserAccountActivatedException, NotProviderException {
        FishingInstructor fishingInstructor = setFishingInstructorAcceptOrDeleteRegistrationRequest();
        fishingInstructor.setEnabled(true);

        when(userRepositoryMock.findById(NEW_USER_ID)).thenReturn(Optional.of(fishingInstructor));

        userService.acceptRegistrationRequest(NEW_USER_ID);

        verify(userRepositoryMock, times(2)).findById(NEW_USER_ID);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    public void declineRegistrationRequest() throws UserNotFoundException, UserAccountActivatedException, NotProviderException {
        FishingInstructor fishingInstructor = setFishingInstructorAcceptOrDeleteRegistrationRequest();
        fishingInstructor.setEnabled(false);

        when(userRepositoryMock.findById(NEW_USER_ID)).thenReturn(Optional.of(fishingInstructor));

        userService.declineRegistrationRequest(NEW_USER_ID, DECLINE_REASON);
        User user = userService.findById(NEW_USER_ID);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(NEW_USER_ID);
        assertThat(user.isEnabled()).isEqualTo(false);

        verify(userRepositoryMock, times(5)).findById(NEW_USER_ID);
        verify(userRepositoryMock, times(1)).delete(fishingInstructor);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test(expected = UserNotFoundException.class)
    public void declineRegistrationRequestForUserNotFound() throws UserNotFoundException, UserAccountActivatedException, NotProviderException {
        when(userRepositoryMock.findById(NEW_USER_ID)).thenReturn(Optional.empty());

        userService.declineRegistrationRequest(NEW_USER_ID, DECLINE_REASON);

        verify(userRepositoryMock, times(1)).findById(NEW_USER_ID);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test(expected = UserAccountActivatedException.class)
    public void declineRegistrationRequestForActivatedUser() throws UserNotFoundException, UserAccountActivatedException, NotProviderException {
        FishingInstructor fishingInstructor = setFishingInstructorAcceptOrDeleteRegistrationRequest();
        fishingInstructor.setEnabled(true);

        when(userRepositoryMock.findById(NEW_USER_ID)).thenReturn(Optional.of(fishingInstructor));

        userService.declineRegistrationRequest(NEW_USER_ID, DECLINE_REASON);

        verify(userRepositoryMock, times(2)).findById(NEW_USER_ID);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    private FishingInstructor setFishingInstructorSaveRegistrationRequest() {
        FishingInstructor fishingInstructor = new FishingInstructor();
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
        return fishingInstructor;
    }

    private FishingInstructor setFishingInstructorAcceptOrDeleteRegistrationRequest() {
        FishingInstructor fishingInstructor = setFishingInstructorSaveRegistrationRequest();
        fishingInstructor.setId(NEW_USER_ID);
        fishingInstructor.setEmail(NEW_EMAIL);
        fishingInstructor.setPassword(NEW_PASSWORD_ENCODED);
        fishingInstructor.setRole(new Role(DB_ROLE_ID, DB_ROLE_NAME));
        return fishingInstructor;
    }
}
