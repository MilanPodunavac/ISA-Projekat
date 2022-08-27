package code.model.wrappers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Time;

import javax.persistence.Column;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
public class DateRange {
    @Column
    private Date startDate;
    @Column
    private Date endDate;

    public DateRange(Date start, Date end){
        if(start.after(end)){
            startDate = end;
            endDate = start;
        }
        else{
            startDate = start;
            endDate = end;
        }
    }
    public boolean includesEqual(Date date){
        //return (startDate.before(date) || startDate.equals(date)) && (endDate.after(date) || endDate.equals(date));
        return startDate.getTime() <= date.getTime() && endDate.getTime() >= date.getTime();
    }
    public boolean includes(Date date){
        //return startDate.before(date) && endDate.after(date);
        return startDate.getTime() < date.getTime() && endDate.getTime() > date.getTime();
    }
    public boolean includes(DateRange dateRange) {
        return includesEqual(dateRange.startDate) && includesEqual(dateRange.endDate);
    }
    public boolean overlapsWith(DateRange dateRange){
        return dateRange.includes(startDate) || includes(dateRange.startDate) || startDate.getTime() == dateRange.startDate.getTime(); //startDate.equals(dateRange.startDate);
    }
    public boolean DateRangeInPast()
    {
        return endDate.getTime() < Time.now();
    }
    public int durationInDays(){
        return (int)TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);
    }
}
