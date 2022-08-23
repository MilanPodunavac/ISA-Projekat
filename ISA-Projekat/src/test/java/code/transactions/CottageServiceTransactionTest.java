package code.transactions;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.Client;
import code.model.cottage.Cottage;
import code.model.cottage.CottageReservation;
import code.model.wrappers.DateRange;
import code.repository.ClientRepository;
import code.repository.CottageRepository;
import code.service.CottageService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CottageServiceTransactionTest {
    @Autowired
    private CottageService _cottageService;
    @Autowired
    private CottageRepository _cottageRepository;
    @Autowired
    private ClientRepository _clientRepository;

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticReservationLock() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {
            @Override
            public void run() {
                CottageReservation reservation = new CottageReservation();
                Cottage cottage = _cottageRepository.findById(1).get();
                Client client = _clientRepository.findById(9).get();
                reservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 25).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 26).getTime()));
                reservation.setNumberOfPeople(2);
                reservation.setCottageReservationTag(new HashSet<>());
                reservation.setClient(client);
                try {
                    cottage.addReservation(reservation);
                } catch (InvalidReservationException e) {
                    throw new RuntimeException(e);
                } catch (ClientCancelledThisPeriodException e) {
                    throw new RuntimeException(e);
                }
                try { Thread.sleep(5000); } catch (InterruptedException e){}
                _cottageRepository.save(cottage);
            }
        });
        executor.submit(new Runnable() {
            @Override
            public void run() {
                CottageReservation reservation = new CottageReservation();
                Cottage cottage = _cottageRepository.findById(1).get();
                Client client = _clientRepository.findById(9).get();
                reservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 25).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 26).getTime()));
                reservation.setNumberOfPeople(2);
                reservation.setCottageReservationTag(new HashSet<>());
                reservation.setClient(client);
                try {
                    cottage.addReservation(reservation);
                } catch (InvalidReservationException e) {
                    throw new RuntimeException(e);
                } catch (ClientCancelledThisPeriodException e) {
                    throw new RuntimeException(e);
                }
                _cottageRepository.save(cottage);
            }
        });
        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
