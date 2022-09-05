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
public class YearlyVisitReport {
    private SaleEntityVisitPeriod first;
    private SaleEntityVisitPeriod second;
    private SaleEntityVisitPeriod third;
    private SaleEntityVisitPeriod fourth;
    private SaleEntityVisitPeriod fifth;
    private SaleEntityVisitPeriod sixth;
    private SaleEntityVisitPeriod seventh;
    private SaleEntityVisitPeriod eighth;
    private SaleEntityVisitPeriod ninth;
    private SaleEntityVisitPeriod tenth;
    private SaleEntityVisitPeriod eleventh;
    private SaleEntityVisitPeriod twelfth;

    public void initiate(){
        Date now = new Date(System.currentTimeMillis());
        now.setDate(1);
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        now.setYear(now.getYear() - 1);
        first = new SaleEntityVisitPeriod();
        first.setStart(now);

        second = new SaleEntityVisitPeriod();
        second.setStart(((Date) first.getStart().clone()));
        second.getStart().setMonth(second.getStart().getMonth() + 1);

        third = new SaleEntityVisitPeriod();
        third.setStart(((Date) second.getStart().clone()));
        third.getStart().setMonth(third.getStart().getMonth() + 1);

        fourth = new SaleEntityVisitPeriod();
        fourth.setStart(((Date) third.getStart().clone()));
        fourth.getStart().setMonth(fourth.getStart().getMonth() + 1);

        fifth = new SaleEntityVisitPeriod();
        fifth.setStart(((Date) fourth.getStart().clone()));
        fifth.getStart().setMonth(fifth.getStart().getMonth() + 1);

        sixth = new SaleEntityVisitPeriod();
        sixth.setStart(((Date) fifth.getStart().clone()));
        sixth.getStart().setMonth(sixth.getStart().getMonth() + 1);

        seventh = new SaleEntityVisitPeriod();
        seventh.setStart(((Date) sixth.getStart().clone()));
        seventh.getStart().setMonth(seventh.getStart().getMonth() + 1);

        eighth = new SaleEntityVisitPeriod();
        eighth.setStart(((Date) seventh.getStart().clone()));
        eighth.getStart().setMonth(eighth.getStart().getMonth() + 1);

        ninth = new SaleEntityVisitPeriod();
        ninth.setStart(((Date) eighth.getStart().clone()));
        ninth.getStart().setMonth(ninth.getStart().getMonth() + 1);

        tenth = new SaleEntityVisitPeriod();
        tenth.setStart(((Date) ninth.getStart().clone()));
        tenth.getStart().setMonth(tenth.getStart().getMonth() + 1);

        eleventh = new SaleEntityVisitPeriod();
        eleventh.setStart(((Date) tenth.getStart().clone()));
        eleventh.getStart().setMonth(eleventh.getStart().getMonth() + 1);

        twelfth = new SaleEntityVisitPeriod();
        twelfth.setStart(((Date) eleventh.getStart().clone()));
        twelfth.getStart().setMonth(twelfth.getStart().getMonth() + 1);

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
                && fourth.getStart().after(res.getDateRange().getStartDate())){
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
        if(seventh.getStart().before(copy)
                && eighth.getStart().after(copy)){
            seventh.setVisitNumber(seventh.getVisitNumber() + 1);
            return;
        }
        if(eighth.getStart().before(copy)
                && ninth.getStart().after(copy)){
            eighth.setVisitNumber(eighth.getVisitNumber() + 1);
            return;
        }
        if(ninth.getStart().before(copy)
                && tenth.getStart().after(copy)){
            ninth.setVisitNumber(ninth.getVisitNumber() + 1);
            return;
        }
        if(tenth.getStart().before(copy)
                && eleventh.getStart().after(copy)){
            tenth.setVisitNumber(tenth.getVisitNumber() + 1);
            return;
        }
        if(eleventh.getStart().before(copy)
                && twelfth.getStart().after(copy)){
            eleventh.setVisitNumber(eleventh.getVisitNumber() + 1);
            return;
        }
        if(twelfth.getStart().before(copy) && now.after(copy)){
            twelfth.setVisitNumber(twelfth.getVisitNumber() + 1);
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
        if(fourth.getStart().before(copy)
                && fifth.getStart().after(copy)){
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
        if(seventh.getStart().before(copy)
                && eighth.getStart().after(copy)){
            seventh.setVisitNumber(seventh.getVisitNumber() + 1);
            return;
        }
        if(eighth.getStart().before(copy)
                && ninth.getStart().after(copy)){
            eighth.setVisitNumber(eighth.getVisitNumber() + 1);
            return;
        }
        if(ninth.getStart().before(copy)
                && tenth.getStart().after(copy)){
            ninth.setVisitNumber(ninth.getVisitNumber() + 1);
            return;
        }
        if(tenth.getStart().before(copy)
                && eleventh.getStart().after(copy)){
            tenth.setVisitNumber(tenth.getVisitNumber() + 1);
            return;
        }
        if(eleventh.getStart().before(copy)
                && twelfth.getStart().after(copy)){
            eleventh.setVisitNumber(eleventh.getVisitNumber() + 1);
            return;
        }
        if(twelfth.getStart().before(copy) && now.after(copy)){
            twelfth.setVisitNumber(twelfth.getVisitNumber() + 1);
        }
    }

}
