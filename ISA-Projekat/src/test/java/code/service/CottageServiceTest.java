package code.service;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.model.wrappers.DateRange;
import code.repository.CottageRepository;
import code.repository.UserRepository;
import code.service.impl.CottageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Optional;

import static code.constants.AdminConstants.NEW_FIRST_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CottageServiceTest {

    @Mock
    private UserRepository _userRepository;

    @Mock
    private CottageRepository _cottageRepository;

    @Mock
    private JavaMailSender _mailSender;

    @InjectMocks
    private CottageServiceImpl _cottageService;

    @Test
    public void addAvailabilityPeriodSuccess() throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException {
        //ARRANGE
        CottageOwner cottageOwner = getMockCottageOwner();
        Cottage cottage = getMockCottage(cottageOwner);
        AvailabilityPeriod period = new AvailabilityPeriod();
        period.setRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 3).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 30).getTime()));
        when(_userRepository.findByEmail(cottageOwner.getEmail())).thenReturn(cottageOwner);
        when(_cottageRepository.findById(cottage.getId())).thenReturn(Optional.of(cottage));
        when(_cottageRepository.save(cottage)).thenReturn(cottage);
        //ACT
        _cottageService.addAvailabilityPeriod(cottage.getId(), period, cottageOwner.getEmail());
        //ASSERT
        assertThat(cottage.getAvailabilityPeriods().size()).isEqualTo(2);
        verify(_userRepository, times(1)).findByEmail(cottageOwner.getEmail());
        verify(_cottageRepository, times(1)).save(cottage);
        verify(_cottageRepository, times(1)).findById(cottage.getId());
    }

    @Test(expected = UnauthorizedAccessException.class)
    public void addAvailabilityPeriodOwnerNotFound() throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException {
        //ARRANGE
        CottageOwner cottageOwner = getMockCottageOwner();
        Cottage cottage = getMockCottage(cottageOwner);
        AvailabilityPeriod period = new AvailabilityPeriod();
        period.setRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 3).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 30).getTime()));
        when(_userRepository.findByEmail("invalid")).thenReturn(null);
        when(_cottageRepository.findById(cottage.getId())).thenReturn(Optional.of(cottage));
        when(_cottageRepository.save(cottage)).thenReturn(cottage);
        //ACT
        _cottageService.addAvailabilityPeriod(cottage.getId(), period, "invalid");
        //ASSERT
        //Exception expected
    }

    @Test(expected = EntityNotFoundException.class)
    public void addAvailabilityPeriodCottageNotFound() throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException {
        //ARRANGE
        CottageOwner cottageOwner = getMockCottageOwner();
        Cottage cottage = getMockCottage(cottageOwner);
        AvailabilityPeriod period = new AvailabilityPeriod();
        period.setRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 3).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 30).getTime()));
        when(_userRepository.findByEmail(cottageOwner.getEmail())).thenReturn(cottageOwner);
        when(_cottageRepository.findById(2344235)).thenReturn(Optional.empty());
        when(_cottageRepository.save(cottage)).thenReturn(cottage);
        //ACT
        _cottageService.addAvailabilityPeriod(2344235, period, cottageOwner.getEmail());
        //ASSERT
        //Exception expected
    }

    @Test
    public void addReservationSuccess() throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotAvailableException, InvalidReservationException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException {
        //ARRANGE
        CottageOwner cottageOwner = getMockCottageOwner();
        Cottage cottage = getMockCottage(cottageOwner);
        Client client = getMockClient();
        when(_userRepository.findByEmail(cottageOwner.getEmail())).thenReturn(cottageOwner);
        when(_userRepository.findByEmail(client.getEmail())).thenReturn(client);
        when(_cottageRepository.findById(cottage.getId())).thenReturn(Optional.of(cottage));
        when(_cottageRepository.save(cottage)).thenReturn(cottage);
        CottageReservation reservation = new CottageReservation();
        reservation.setCottageReservationTag(new HashSet<>());
        reservation.setNumberOfPeople(2);
        reservation.setCottageReservationTag(new HashSet<>());
        reservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 5).getTime(), new GregorianCalendar(2022, Calendar.MAY, 7).getTime()));
        //ACT
        _cottageService.addReservation(client.getEmail(), cottage.getId(), reservation, cottageOwner.getEmail());
        //ASSERT
        assertThat(reservation.getPrice()).isEqualTo(200);
        assertThat(reservation.getClient().getId()).isEqualTo(client.getId());
        verify(_userRepository, times(1)).findByEmail(cottageOwner.getEmail());
        verify(_userRepository, times(1)).findByEmail(client.getEmail());
        verify(_cottageRepository, times(1)).findById(cottage.getId());
        verify(_cottageRepository, times(1)).save(cottage);
        verify(_mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test(expected = InvalidReservationException.class)
    public void addReservationNotEnoughBeds() throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotAvailableException, InvalidReservationException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException {
        //ARRANGE
        CottageOwner cottageOwner = getMockCottageOwner();
        Cottage cottage = getMockCottage(cottageOwner);
        Client client = getMockClient();
        when(_userRepository.findByEmail(cottageOwner.getEmail())).thenReturn(cottageOwner);
        when(_userRepository.findByEmail(client.getEmail())).thenReturn(client);
        when(_cottageRepository.findById(cottage.getId())).thenReturn(Optional.of(cottage));
        when(_cottageRepository.save(cottage)).thenReturn(cottage);
        CottageReservation reservation = new CottageReservation();
        reservation.setCottageReservationTag(new HashSet<>());
        reservation.setNumberOfPeople(7);
        reservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 5).getTime(), new GregorianCalendar(2022, Calendar.MAY, 7).getTime()));
        //ACT
        _cottageService.addReservation(client.getEmail(), cottage.getId(), reservation, cottageOwner.getEmail());
        //ASSERT
        //Exception expected
    }

    @Test(expected = EntityNotAvailableException.class)
    public void addReservationNotAvailable() throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotAvailableException, InvalidReservationException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException {
        //ARRANGE
        CottageOwner cottageOwner = getMockCottageOwner();
        Cottage cottage = getMockCottage(cottageOwner);
        Client client = getMockClient();
        when(_userRepository.findByEmail(cottageOwner.getEmail())).thenReturn(cottageOwner);
        when(_userRepository.findByEmail(client.getEmail())).thenReturn(client);
        when(_cottageRepository.findById(cottage.getId())).thenReturn(Optional.of(cottage));
        when(_cottageRepository.save(cottage)).thenReturn(cottage);
        CottageReservation reservation = new CottageReservation();
        reservation.setCottageReservationTag(new HashSet<>());
        reservation.setNumberOfPeople(2);
        reservation.setCottageReservationTag(new HashSet<>());
        reservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 5).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 7).getTime()));
        //ACT
        _cottageService.addReservation(client.getEmail(), cottage.getId(), reservation, cottageOwner.getEmail());
        //ASSERT
        //Exception expected
    }

    @Test(expected = InvalidReservationException.class)
    public void addReservationNoAdditionalService() throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotAvailableException, InvalidReservationException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException {
        //ARRANGE
        CottageOwner cottageOwner = getMockCottageOwner();
        Cottage cottage = getMockCottage(cottageOwner);
        cottage.getAdditionalServices().add(CottageReservationTag.childFriendly);
        Client client = getMockClient();
        when(_userRepository.findByEmail(cottageOwner.getEmail())).thenReturn(cottageOwner);
        when(_userRepository.findByEmail(client.getEmail())).thenReturn(client);
        when(_cottageRepository.findById(cottage.getId())).thenReturn(Optional.of(cottage));
        when(_cottageRepository.save(cottage)).thenReturn(cottage);
        CottageReservation reservation = new CottageReservation();
        reservation.setCottageReservationTag(new HashSet<>());
        reservation.setNumberOfPeople(2);
        reservation.setCottageReservationTag(new HashSet<>());
        reservation.getCottageReservationTag().add(CottageReservationTag.wiFi);
        reservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 5).getTime(), new GregorianCalendar(2022, Calendar.MAY, 7).getTime()));
        //ACT
        _cottageService.addReservation(client.getEmail(), cottage.getId(), reservation, cottageOwner.getEmail());
        //ASSERT
        //Exception expected
    }

    private Client getMockClient() {
        Client client = new Client();
        client.setEmail("TESTCLIENT@gmail.com");
        return client;
    }

    private Cottage getMockCottage(CottageOwner cottageOwner) throws AvailabilityPeriodBadRangeException {
        AvailabilityPeriod period = new AvailabilityPeriod();
        period.setRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 3).getTime(), new GregorianCalendar(2022, Calendar.MAY, 30).getTime()));
        period.setReservations(new HashSet<>());
        Cottage cottage = new Cottage();
        cottage.setId(100);
        cottage.setAvailabilityPeriods(new HashSet<>());
        cottage.addAvailabilityPeriod(period);
        cottage.setBedNumber(2);
        cottage.setRoomNumber(2);
        cottage.setPricePerDay(100);
        cottage.setCottageOwner(cottageOwner);
        cottage.setAdditionalServices(new HashSet<>());
        return cottage;
    }

    private CottageOwner getMockCottageOwner() {
        CottageOwner cottageOwner = new CottageOwner();
        cottageOwner.setEmail("TESTCOTTAGEOWNER@gmail.com");
        cottageOwner.setCottage(new HashSet<>());
        return cottageOwner;
    }


}
