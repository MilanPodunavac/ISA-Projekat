package code.service;

import code.exceptions.entities.AvailabilityPeriodBadRangeException;
import code.model.AvailabilityPeriod;
import code.model.Client;
import code.model.Cottage;
import code.model.CottageOwner;
import code.model.wrappers.DateRange;
import code.repository.CottageRepository;
import code.repository.UserRepository;
import code.service.impl.CottageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import static org.mockito.Mockito.when;

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
    public void addAvailabilityPeriodSuccess() throws AvailabilityPeriodBadRangeException {
        CottageOwner cottageOwner = new CottageOwner();
        cottageOwner.setEmail("TESTCOTTAGEOWNER@gmail.com");
        cottageOwner.setCottage(new HashSet<>());
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
        Client client = new Client();
        when(_userRepository.findByEmail(cottageOwner.getEmail())).thenReturn(cottageOwner);
        //dovrsiti
    }


}
