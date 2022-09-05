package code.model.report;

import code.model.IncomeRecord;
import code.model.base.Action;
import code.model.base.Reservation;
import code.model.base.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyVisitReport {
    private SaleEntityVisitPeriod first;
    private SaleEntityVisitPeriod second;
    private SaleEntityVisitPeriod third;
    private SaleEntityVisitPeriod fourth;
    public void initiate(){
        Date now = new Date(System.currentTimeMillis());
        now.setDate(1);
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        now.setDate(now.getDate() - 28);
        first = new SaleEntityVisitPeriod();
        first.setStart(now);

        second = new SaleEntityVisitPeriod();
        second.setStart(((Date) first.getStart().clone()));
        second.getStart().setDate(second.getStart().getDate() + 7);

        third = new SaleEntityVisitPeriod();
        third.setStart(((Date) second.getStart().clone()));
        third.getStart().setDate(third.getStart().getDate() + 7);

        fourth = new SaleEntityVisitPeriod();
        fourth.setStart(((Date) third.getStart().clone()));
        fourth.getStart().setDate(fourth.getStart().getDate() + 7);

    }
    public void addVisit(Reservation res){
        Date now = new Date(System.currentTimeMillis());
        now.setDate(1);
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        if(res.getReservationStatus() == ReservationStatus.cancelled)return;
        Date copy = (Date) res.getDateRange().getStartDate().clone();
        copy.setSeconds(copy.getSeconds() + 1);
        if(first.getStart().before(copy)
                && second.getStart().after(copy)){
            first.setVisitNumber(first.getVisitNumber() + 1);
            return;
        }
        if(second.getStart().before(copy)
                && third.getStart().after(copy)){
            second.setVisitNumber(second.getVisitNumber() + 1);
            return;
        }
        if(third.getStart().before(copy)
                && fourth.getStart().after(copy)){
            third.setVisitNumber(third.getVisitNumber() + 1);
            return;
        }
        if(fourth.getStart().before(copy) && now.after(copy)) {
            fourth.setVisitNumber(fourth.getVisitNumber() + 1);
        }
    }
    public void addVisit(Action act){
        Date now = new Date(System.currentTimeMillis());
        now.setDate(1);
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        if(!act.isReserved())return;
        Date copy = (Date) act.getRange().getStartDate().clone();
        copy.setSeconds(copy.getSeconds() + 1);
        if(first.getStart().before(copy)
                && second.getStart().after(copy)){
            first.setVisitNumber(first.getVisitNumber() + 1);
            return;
        }
        if(second.getStart().before(copy)
                && third.getStart().after(copy)){
            second.setVisitNumber(second.getVisitNumber() + 1);
            return;
        }
        if(third.getStart().before(copy)
                && fourth.getStart().after(copy)){
            third.setVisitNumber(third.getVisitNumber() + 1);
            return;
        }
        if(fourth.getStart().before(copy) && now.after(copy)){
            fourth.setVisitNumber(fourth.getVisitNumber() + 1);
        }
    }

}
