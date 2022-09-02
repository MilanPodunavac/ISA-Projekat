package code.model.report;

import code.model.base.Action;
import code.model.base.Reservation;
import code.model.base.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyVisitReport {
    private SaleEntityVisitPeriod first;
    private SaleEntityVisitPeriod second;
    private SaleEntityVisitPeriod third;
    private SaleEntityVisitPeriod fourth;
    private SaleEntityVisitPeriod fifth;
    private SaleEntityVisitPeriod sixth;
    private SaleEntityVisitPeriod seventh;
    public void initiate(){
        Date now = new Date(System.currentTimeMillis());
        now.setDate(1);
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        now.setDate(now.getDate() - 7);
        first = new SaleEntityVisitPeriod();
        first.setStart(now);

        second = new SaleEntityVisitPeriod();
        second.setStart(((Date) first.getStart().clone()));
        second.getStart().setDate(second.getStart().getDate() + 1);

        third = new SaleEntityVisitPeriod();
        third.setStart(((Date) second.getStart().clone()));
        third.getStart().setDate(third.getStart().getDate() + 1);

        fourth = new SaleEntityVisitPeriod();
        fourth.setStart(((Date) third.getStart().clone()));
        fourth.getStart().setDate(fourth.getStart().getDate() + 1);

        fifth = new SaleEntityVisitPeriod();
        fifth.setStart(((Date) fourth.getStart().clone()));
        fifth.getStart().setDate(fifth.getStart().getDate() + 1);

        sixth = new SaleEntityVisitPeriod();
        sixth.setStart(((Date) fifth.getStart().clone()));
        sixth.getStart().setDate(sixth.getStart().getDate() + 1);

        seventh = new SaleEntityVisitPeriod();
        seventh.setStart(((Date) sixth.getStart().clone()));
        seventh.getStart().setDate(seventh.getStart().getDate() + 1);

    }

    public void addVisit(Reservation res){
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
        if(fourth.getStart().before(copy)
                && fifth.getStart().after(res.getDateRange().getStartDate())){
            fourth.setVisitNumber(fourth.getVisitNumber() + 1);
            return;
        }
        if(fifth.getStart().before(copy)
                && sixth.getStart().after(copy)){
            fifth.setVisitNumber(fifth.getVisitNumber() + 1);
            return;
        }
        if(sixth.getStart().before(copy)
                && seventh.getStart().after(copy)){
            sixth.setVisitNumber(sixth.getVisitNumber() + 1);
            return;
        }
        if(seventh.getStart().before(copy)){
            seventh.setVisitNumber(seventh.getVisitNumber() + 1);
        }
    }

    public void addVisit(Action act){
        if(!act.isReserved())return;
        Date copy = (Date) act.getRange().getStartDate().clone();
        copy.setSeconds(copy.getSeconds() + 1);
        if(first.getStart().before(copy)
                && second.getStart().after(copy)){
            first.setVisitNumber(first.getVisitNumber() + 1);
            return;
        }
        if(second.getStart().before(copy)
                && third.getStart().after(act.getRange().getStartDate())){
            second.setVisitNumber(second.getVisitNumber() + 1);
            return;
        }
        if(third.getStart().before(copy)
                && fourth.getStart().after(act.getRange().getStartDate())){
            third.setVisitNumber(third.getVisitNumber() + 1);
            return;
        }
        if(fourth.getStart().before(copy)
                && fifth.getStart().after(act.getRange().getStartDate())){
            fourth.setVisitNumber(fourth.getVisitNumber() + 1);
            return;
        }
        if(fifth.getStart().before(copy)
                && sixth.getStart().after(act.getRange().getStartDate())){
            fifth.setVisitNumber(fifth.getVisitNumber() + 1);
            return;
        }
        if(sixth.getStart().before(copy)
                && seventh.getStart().after(act.getRange().getStartDate())){
            sixth.setVisitNumber(sixth.getVisitNumber() + 1);
            return;
        }
        if(seventh.getStart().before(copy)){
            seventh.setVisitNumber(seventh.getVisitNumber() + 1);
        }
    }
}
