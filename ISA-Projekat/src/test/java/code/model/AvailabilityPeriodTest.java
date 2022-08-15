package code.model;

import code.model.base.Reservation;
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
public class AvailabilityPeriodTest {
    @ClassRule
    public static final SpringClassRule scr = new SpringClassRule();

    @Rule
    public final SpringMethodRule smr = new SpringMethodRule();

    @AllArgsConstructor
    public static class AddReservationTestsData{
        public AvailabilityPeriod period;
        public CottageReservation newReservation;
        public boolean addShouldBe;
        public int sizeShouldBe;
    }

    @DataPoint
    public static AddReservationTestsData data1 = getAddReservationTestData1();

    @DataPoint
    public static AddReservationTestsData data2 = getAddReservationTestData2();

    @DataPoint
    public static AddReservationTestsData data3 = getAddReservationTestData3();

    @DataPoint
    public static AddReservationTestsData data4 = getAddReservationTestData4();

    @DataPoint
    public static AddReservationTestsData data5 = getAddReservationTestData5();

    @DataPoint
    public static AddReservationTestsData data6 = getAddReservationTestData6();

    @DataPoint
    public static AddReservationTestsData data7 = getAddReservationTestData7();

    @Theory
    public void AddReservationTests(AddReservationTestsData data){
        boolean assertion = data.period.addReservation(data.newReservation);
        Assert.assertEquals(data.addShouldBe, assertion);
        Assert.assertEquals(data.period.getReservations().size(), data.sizeShouldBe);
        boolean isAvailable = data.period.isAvailable(data.newReservation.getDateRange());
        Assert.assertEquals(false, isAvailable);
    }

    private static AddReservationTestsData getAddReservationTestData1(){
        AvailabilityPeriod period = getAvailabilityPeriod();
        CottageReservation resNew = new CottageReservation();
        resNew.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 3).getTime(), new GregorianCalendar(2022, Calendar.MAY, 4).getTime()));
        return new AddReservationTestsData(period, resNew, true, 4);
    }

    private static AddReservationTestsData getAddReservationTestData2(){
        AvailabilityPeriod period = getAvailabilityPeriod();
        CottageReservation resNew = new CottageReservation();
        resNew.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 3).getTime(), new GregorianCalendar(2022, Calendar.MAY, 5).getTime()));
        return new AddReservationTestsData(period, resNew, false, 3);
    }

    private static AddReservationTestsData getAddReservationTestData3(){
        AvailabilityPeriod period = getAvailabilityPeriod();
        CottageReservation resNew = new CottageReservation();
        resNew.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 10).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()));
        return new AddReservationTestsData(period, resNew, true, 4);
    }

    private static AddReservationTestsData getAddReservationTestData4(){
        AvailabilityPeriod period = getAvailabilityPeriod();
        CottageReservation resNew = new CottageReservation();
        resNew.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 2).getTime(), new GregorianCalendar(2022, Calendar.MAY, 6).getTime()));
        return new AddReservationTestsData(period, resNew, false, 3);
    }

    private static AddReservationTestsData getAddReservationTestData5(){
        AvailabilityPeriod period = getAvailabilityPeriod();
        CottageReservation resNew = new CottageReservation();
        resNew.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.APRIL, 2).getTime(), new GregorianCalendar(2022, Calendar.MAY, 6).getTime()));
        return new AddReservationTestsData(period, resNew, false, 3);
    }

    private static AddReservationTestsData getAddReservationTestData6(){
        AvailabilityPeriod period = getAvailabilityPeriod();
        CottageReservation resNew = new CottageReservation();
        resNew.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 12).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 6).getTime()));
        return new AddReservationTestsData(period, resNew, false, 3);
    }

    private static AddReservationTestsData getAddReservationTestData7(){
        AvailabilityPeriod period = getAvailabilityPeriod();
        CottageReservation resNew = new CottageReservation();
        resNew.setDateRange(new DateRange(new GregorianCalendar(2022, Calendar.APRIL, 12).getTime(), new GregorianCalendar(2022, Calendar.JUNE, 6).getTime()));
        return new AddReservationTestsData(period, resNew, false, 3);
    }

    private static AvailabilityPeriod getAvailabilityPeriod(){
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
}
