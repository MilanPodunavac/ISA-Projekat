package code.model.report;

import code.model.base.Action;
import code.model.base.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VisitReport {
    private YearlyVisitReport yearlyVisitReport;
    private MonthlyVisitReport monthlyVisitReport;
    private WeeklyVisitReport weeklyVisitReport;

    public VisitReport(){
        yearlyVisitReport = new YearlyVisitReport();
        monthlyVisitReport = new MonthlyVisitReport();
        weeklyVisitReport = new WeeklyVisitReport();
        yearlyVisitReport.initiate();
        monthlyVisitReport.initiate();
        weeklyVisitReport.initiate();
    }

    public void addVisit(Reservation res){
        yearlyVisitReport.addVisit(res);
        monthlyVisitReport.addVisit(res);
        weeklyVisitReport.addVisit(res);
    }
    public void addVisit(Action action){
        yearlyVisitReport.addVisit(action);
        monthlyVisitReport.addVisit(action);
        weeklyVisitReport.addVisit(action);
    }
}
