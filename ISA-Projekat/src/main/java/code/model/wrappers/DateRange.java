package code.model.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Time;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DateRange {
    private Date startDate;
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
        return (startDate.before(date) || startDate.equals(date)) && (endDate.after(date) || endDate.equals(date));
    }
    public boolean includes(Date date){
        return startDate.before(date) && endDate.after(date);
    }
    public boolean includes(DateRange dateRange) {
        return includesEqual(dateRange.startDate) && includesEqual(dateRange.endDate);
    }
    public boolean overlapsWith(DateRange dateRange){
        return dateRange.includes(startDate) || includes(dateRange.startDate) || startDate.equals(dateRange.startDate);
    }
    public boolean isInPast()
    {
        return endDate.getTime() < Time.now();
    }
}
