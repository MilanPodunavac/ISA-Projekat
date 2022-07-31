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
@AllArgsConstructor
public class DateRange {
    private Date startDate;
    private Date endDate;
    public boolean includes(Date date){
        return startDate.before(date) && endDate.after(date);
    }
    public boolean overlapsWith(DateRange dateRange){
        return dateRange.includes(startDate) || includes(dateRange.startDate);
    }
    public boolean isInPast()
    {
        return endDate.getTime() < Time.now();
    }
}
