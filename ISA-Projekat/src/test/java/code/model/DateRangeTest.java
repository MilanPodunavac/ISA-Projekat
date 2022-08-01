package code.model;

import code.model.wrappers.DateRange;
import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(Theories.class)
@SpringBootTest
public class DateRangeTest {
    @ClassRule
    public static final SpringClassRule scr = new SpringClassRule();

    @Rule
    public final SpringMethodRule smr = new SpringMethodRule();

    @AllArgsConstructor
    public static class DateRangeComparisonData{
        public DateRange range1;
        public DateRange range2;
        public boolean includesShouldBe;
        public boolean overlapsShouldBe;
    }

    @DataPoint
    public static DateRangeComparisonData data1 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 30).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 2).getTime()),
            true, true);
    @DataPoint
    public static DateRangeComparisonData data2 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 2).getTime(), new GregorianCalendar(2022, Calendar.MAY, 30).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 2).getTime()),
            false,false);
    @DataPoint
    public static DateRangeComparisonData data3 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 30).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 30).getTime()),
            true, true);
    @DataPoint
    public static DateRangeComparisonData data4 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 21).getTime()),
            false, true);
    @DataPoint
    public static DateRangeComparisonData data5 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 30).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 2).getTime(), new GregorianCalendar(2022, Calendar.MAY, 28).getTime()),
            true, true);
    @DataPoint
    public static DateRangeComparisonData data6 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 10).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 2).getTime()),
            false, false);
    @DataPoint
    public static DateRangeComparisonData data7 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 10).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 21).getTime()),
            false,true);
    @DataPoint
    public static DateRangeComparisonData data8 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 10).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 24).getTime(), new GregorianCalendar(2022, Calendar.MAY, 28).getTime()),
            false, false);
    @DataPoint
    public static DateRangeComparisonData data9 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 10).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 19).getTime(), new GregorianCalendar(2022, Calendar.MAY, 28).getTime()),
            false, true);
    @DataPoint
    public static DateRangeComparisonData data10 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 10).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 15).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()),
            true, true);
    @DataPoint
    public static DateRangeComparisonData data11 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 10).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 20).getTime(), new GregorianCalendar(2022, Calendar.MAY, 21).getTime()),
            false, false);
    @DataPoint
    public static DateRangeComparisonData data12 = new DateRangeComparisonData(
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 10).getTime(), new GregorianCalendar(2022, Calendar.MAY, 20).getTime()),
            new DateRange(new GregorianCalendar(2022, Calendar.MAY, 21).getTime(), new GregorianCalendar(2022, Calendar.MAY, 22).getTime()),
            false, false);

    @Theory
    public void includesDateRange(DateRangeComparisonData data){
        Assert.assertEquals(data.includesShouldBe, data.range1.includes(data.range2));
    }
    @Theory
    public void overlapsDateRange(DateRangeComparisonData data){
        Assert.assertEquals(data.overlapsShouldBe, data.range1.overlapsWith(data.range2));
    }
}
