package code.model;

import code.exceptions.entities.AvailabilityPeriodBadRangeException;
import code.exceptions.entities.ClientCancelledThisPeriodException;
import code.exceptions.entities.InvalidReservationException;
import code.model.base.AvailabilityPeriod;
import code.model.base.Reservation;
import code.model.cottage.Cottage;
import code.model.cottage.CottageOwner;
import code.model.cottage.CottageReservation;
import code.model.wrappers.DateRange;
import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

@RunWith(Theories.class)
@SpringBootTest
public class SaleEntityTest {
    @ClassRule
    public static final SpringClassRule scr = new SpringClassRule();

    @Rule
    public final SpringMethodRule smr = new SpringMethodRule();

    @AllArgsConstructor
    public static class AddReservationTestData{
        public Cottage cottage;
        public CottageReservation newReservation;
        public boolean shouldBe;
    }

    @DataPoint
    public static AddReservationTestData data1;

    static {
        try {
            data1 = getAddReservationTestData1();
        } catch (AvailabilityPeriodBadRangeException e) {
            throw new RuntimeException(e);
        }
    }

    @DataPoint
    public static AddReservationTestData data2;

    static {
        try {
            data2 = getAddReservationTestData2();
        } catch (AvailabilityPeriodBadRangeException e) {
            throw new RuntimeException(e);
        }
    }

    @DataPoint
    public static AddReservationTestData data3;

    static {
        try {
            data3 = getAddReservationTestData3();
        } catch (AvailabilityPeriodBadRangeException e) {
            throw new RuntimeException(e);
        }
    }

    @DataPoint
    public static AddReservationTestData data4;

    static {
        try {
            data4 = getAddReservationTestData4();
        } catch (AvailabilityPeriodBadRangeException e) {
            throw new RuntimeException(e);
        }
    }

    @DataPoint
    public static AddReservationTestData data5;

    static {
        try {
            data5 = getAddReservationTestData5();
        } catch (AvailabilityPeriodBadRangeException e) {
            throw new RuntimeException(e);
        }
    }

    @DataPoint
    public static AddReservationTestData data6;

    static {
        try {
            data6 = getAddReservationTestData6();
        } catch (AvailabilityPeriodBadRangeException e) {
            throw new RuntimeException(e);
        }
    }

    @Theory
    public void AddReservationTest(AddReservationTestData data) throws InvalidReservationException, ClientCancelledThisPeriodException {
        boolean assertion = data.cottage.addReservation(data.newReservation);
        Assert.assertEquals(data.shouldBe, assertion);
        boolean addAgain = data.cottage.addReservation(data.newReservation);
        Assert.assertEquals(false, addAgain);
    }

    private static AddReservationTestData getAddReservationTestData1() throws AvailabilityPeriodBadRangeException {
        Cottage cottage = new Cottage();
        cottage.setCottageOwner(SetCottageOwner());
        cottage.setAdditionalServices(new HashSet<>());
        cottage.setAvailabilityPeriods(new HashSet<>());
        AvailabilityPeriod period1 = getAvailabilityPeriod1();
        AvailabilityPeriod period2 = getAvailabilityPeriod2();
        AvailabilityPeriod period3 = getAvailabilityPeriod3();
        CottageReservation newReservation = new CottageReservation();
        newReservation.setClient(SetClient());
        newReservation.setCottageReservationTag(new HashSet<>());
        newReservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JULY, 4).getTime(), new GregorianCalendar(2022, Calendar.JULY, 8).getTime()));
        cottage.addAvailabilityPeriod(period1);
        cottage.addAvailabilityPeriod(period2);
        cottage.addAvailabilityPeriod(period3);
        return new AddReservationTestData(cottage, newReservation, true);
    }

    private static AddReservationTestData getAddReservationTestData2() throws AvailabilityPeriodBadRangeException {
        Cottage cottage = new Cottage();
        cottage.setCottageOwner(SetCottageOwner());
        cottage.setAdditionalServices(new HashSet<>());
        cottage.setAvailabilityPeriods(new HashSet<>());
        AvailabilityPeriod period1 = getAvailabilityPeriod1();
        AvailabilityPeriod period2 = getAvailabilityPeriod2();
        AvailabilityPeriod period3 = getAvailabilityPeriod3();
        CottageReservation newReservation = new CottageReservation();
        newReservation.setClient(SetClient());
        newReservation.setCottageReservationTag(new HashSet<>());
        newReservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 3).getTime(), new GregorianCalendar(2022, Calendar.MAY, 4).getTime()));
        cottage.addAvailabilityPeriod(period1);
        cottage.addAvailabilityPeriod(period2);
        cottage.addAvailabilityPeriod(period3);
        return new AddReservationTestData(cottage, newReservation, true);
    }

    private static AddReservationTestData getAddReservationTestData3() throws AvailabilityPeriodBadRangeException {
        Cottage cottage = new Cottage();
        cottage.setCottageOwner(SetCottageOwner());
        cottage.setAdditionalServices(new HashSet<>());
        cottage.setAvailabilityPeriods(new HashSet<>());
        AvailabilityPeriod period1 = getAvailabilityPeriod1();
        AvailabilityPeriod period2 = getAvailabilityPeriod2();
        AvailabilityPeriod period3 = getAvailabilityPeriod3();
        CottageReservation newReservation = new CottageReservation();
        newReservation.setClient(SetClient());
        newReservation.setCottageReservationTag(new HashSet<>());
        newReservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.SEPTEMBER, 4).getTime(), new GregorianCalendar(2022, Calendar.SEPTEMBER, 8).getTime()));
        cottage.addAvailabilityPeriod(period1);
        cottage.addAvailabilityPeriod(period2);
        cottage.addAvailabilityPeriod(period3);
        return new AddReservationTestData(cottage, newReservation, false);
    }

    private static AddReservationTestData getAddReservationTestData4() throws AvailabilityPeriodBadRangeException {
        Cottage cottage = new Cottage();
        cottage.setCottageOwner(SetCottageOwner());
        cottage.setAdditionalServices(new HashSet<>());
        cottage.setAvailabilityPeriods(new HashSet<>());
        AvailabilityPeriod period1 = getAvailabilityPeriod1();
        AvailabilityPeriod period2 = getAvailabilityPeriod2();
        AvailabilityPeriod period3 = getAvailabilityPeriod3();
        CottageReservation newReservation = new CottageReservation();
        newReservation.setClient(SetClient());
        newReservation.setCottageReservationTag(new HashSet<>());
        newReservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 4).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 8).getTime()));
        cottage.addAvailabilityPeriod(period1);
        cottage.addAvailabilityPeriod(period2);
        cottage.addAvailabilityPeriod(period3);
        return new AddReservationTestData(cottage, newReservation, false);
    }

    private static AddReservationTestData getAddReservationTestData5() throws AvailabilityPeriodBadRangeException {
        Cottage cottage = new Cottage();
        cottage.setCottageOwner(SetCottageOwner());
        cottage.setAdditionalServices(new HashSet<>());
        cottage.setAvailabilityPeriods(new HashSet<>());
        AvailabilityPeriod period1 = getAvailabilityPeriod1();
        AvailabilityPeriod period2 = getAvailabilityPeriod2();
        AvailabilityPeriod period3 = getAvailabilityPeriod3();
        CottageReservation newReservation = new CottageReservation();
        newReservation.setClient(SetClient());
        newReservation.setCottageReservationTag(new HashSet<>());
        newReservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.APRIL, 4).getTime(), new GregorianCalendar(2022, Calendar.APRIL, 8).getTime()));
        cottage.addAvailabilityPeriod(period1);
        cottage.addAvailabilityPeriod(period2);
        cottage.addAvailabilityPeriod(period3);
        return new AddReservationTestData(cottage, newReservation, false);
    }

    private static AddReservationTestData getAddReservationTestData6() throws AvailabilityPeriodBadRangeException {
        Cottage cottage = new Cottage();
        cottage.setCottageOwner(SetCottageOwner());
        cottage.setAdditionalServices(new HashSet<>());
        cottage.setAvailabilityPeriods(new HashSet<>());
        AvailabilityPeriod period1 = getAvailabilityPeriod1();
        AvailabilityPeriod period3 = getAvailabilityPeriod3();
        CottageReservation newReservation = new CottageReservation();
        newReservation.setClient(SetClient());
        newReservation.setCottageReservationTag(new HashSet<>());
        newReservation.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 4).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 8).getTime()));
        cottage.addAvailabilityPeriod(period1);
        cottage.addAvailabilityPeriod(period3);
        return new AddReservationTestData(cottage, newReservation, false);
    }

    private static AvailabilityPeriod getAvailabilityPeriod1(){
        AvailabilityPeriod period = new AvailabilityPeriod();
        period.setActions(new HashSet<>());
        period.setRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 30).getTime()));
        period.setReservations(new HashSet<Reservation>());
        CottageReservation res1 = new CottageReservation();
        res1.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 2).getTime()));
        CottageReservation res2 = new CottageReservation();
        res2.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 2).getTime(), new GregorianCalendar(2022, Calendar.MAY, 3).getTime()));
        CottageReservation res3 = new CottageReservation();
        res3.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 4).getTime(), new GregorianCalendar(2022, Calendar.MAY, 10).getTime()));
        period.getReservations().add(res1);
        period.getReservations().add(res2);
        period.getReservations().add(res3);
        return period;
    }

    private static AvailabilityPeriod getAvailabilityPeriod2(){
        AvailabilityPeriod period = new AvailabilityPeriod();
        period.setActions(new HashSet<>());
        period.setRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 1).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 30).getTime()));
        period.setReservations(new HashSet<Reservation>());
        CottageReservation res1 = new CottageReservation();
        res1.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 1).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 2).getTime()));
        CottageReservation res2 = new CottageReservation();
        res2.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 2).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 5).getTime()));
        CottageReservation res3 = new CottageReservation();
        res3.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JUNE, 7).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 16).getTime()));
        period.getReservations().add(res1);
        period.getReservations().add(res2);
        period.getReservations().add(res3);
        return period;
    }

    private static AvailabilityPeriod getAvailabilityPeriod3(){
        AvailabilityPeriod period = new AvailabilityPeriod();
        period.setActions(new HashSet<>());
        period.setRange(new DateRange(new GregorianCalendar(2022, Calendar.JULY, 1).getTime(), new GregorianCalendar(2022, Calendar.JULY, 30).getTime()));
        period.setReservations(new HashSet<Reservation>());
        CottageReservation res1 = new CottageReservation();
        res1.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JULY, 1).getTime(), new GregorianCalendar(2022, Calendar.JULY, 2).getTime()));
        CottageReservation res2 = new CottageReservation();
        res2.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JULY, 2).getTime(), new GregorianCalendar(2022, Calendar.JULY, 3).getTime()));
        CottageReservation res3 = new CottageReservation();
        res3.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.JULY, 10).getTime(), new GregorianCalendar(2022, Calendar.JULY, 16).getTime()));
        period.getReservations().add(res1);
        period.getReservations().add(res2);
        period.getReservations().add(res3);
        return period;
    }

    private static CottageOwner SetCottageOwner(){
        CottageOwner owner = new CottageOwner();
        owner.setLoyaltyPoints(0);
        owner.setCategory(new LoyaltyProgramProvider(1, 0, 0, LoyaltyProgramCategory.regular));
        return owner;
    }

    private static Client SetClient(){
        Client client = new Client();
        client.setLoyaltyPoints(0);
        client.setCategory(new LoyaltyProgramClient(1, 0, 0, LoyaltyProgramCategory.regular));
        return client;
    }
}
