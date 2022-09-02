package code.model.report;

import code.model.IncomeRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YearlyProfitReport {
    private MonthProfitReport first;
    private MonthProfitReport second;
    private MonthProfitReport third;
    private MonthProfitReport fourth;
    private MonthProfitReport fifth;
    private MonthProfitReport sixth;
    private MonthProfitReport seventh;
    private MonthProfitReport eighth;
    private MonthProfitReport ninth;
    private MonthProfitReport tenth;
    private MonthProfitReport eleventh;
    private MonthProfitReport twelfth;

    public void initiate(){
        Date now = new Date(System.currentTimeMillis());
        now.setDate(1);
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        now.setYear(now.getYear() - 1);
        first = new MonthProfitReport();
        first.setStart(now);

        second = new MonthProfitReport();
        second.setStart(((Date) first.getStart().clone()));
        second.getStart().setMonth(second.getStart().getMonth() + 1);

        third = new MonthProfitReport();
        third.setStart(((Date) second.getStart().clone()));
        third.getStart().setMonth(third.getStart().getMonth() + 1);

        fourth = new MonthProfitReport();
        fourth.setStart(((Date) third.getStart().clone()));
        fourth.getStart().setMonth(fourth.getStart().getMonth() + 1);

        fifth = new MonthProfitReport();
        fifth.setStart(((Date) fourth.getStart().clone()));
        fifth.getStart().setMonth(fifth.getStart().getMonth() + 1);

        sixth = new MonthProfitReport();
        sixth.setStart(((Date) fifth.getStart().clone()));
        sixth.getStart().setMonth(sixth.getStart().getMonth() + 1);

        seventh = new MonthProfitReport();
        seventh.setStart(((Date) sixth.getStart().clone()));
        seventh.getStart().setMonth(seventh.getStart().getMonth() + 1);

        eighth = new MonthProfitReport();
        eighth.setStart(((Date) seventh.getStart().clone()));
        eighth.getStart().setMonth(eighth.getStart().getMonth() + 1);

        ninth = new MonthProfitReport();
        ninth.setStart(((Date) eighth.getStart().clone()));
        ninth.getStart().setMonth(ninth.getStart().getMonth() + 1);

        tenth = new MonthProfitReport();
        tenth.setStart(((Date) ninth.getStart().clone()));
        tenth.getStart().setMonth(tenth.getStart().getMonth() + 1);

        eleventh = new MonthProfitReport();
        eleventh.setStart(((Date) tenth.getStart().clone()));
        eleventh.getStart().setMonth(eleventh.getStart().getMonth() + 1);

        twelfth = new MonthProfitReport();
        twelfth.setStart(((Date) eleventh.getStart().clone()));
        twelfth.getStart().setMonth(twelfth.getStart().getMonth() + 1);

    }

    public void addIncome(IncomeRecord rec){
        if(first.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && second.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            first.setIncome(first.getIncome() + rec.getProviderIncome());
        }
        if(second.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && third.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            second.setIncome(second.getIncome() + rec.getProviderIncome());
        }
        if(third.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && fourth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            third.setIncome(third.getIncome() + rec.getProviderIncome());
        }
        if(fourth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && fifth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            fourth.setIncome(fourth.getIncome() + rec.getProviderIncome());
        }
        if(fifth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && sixth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            fifth.setIncome(fifth.getIncome() + rec.getProviderIncome());
        }
        if(sixth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && seventh.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            sixth.setIncome(sixth.getIncome() + rec.getProviderIncome());
        }
        if(seventh.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && eighth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            seventh.setIncome(seventh.getIncome() + rec.getProviderIncome());
        }
        if(eighth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && ninth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            eighth.setIncome(eighth.getIncome() + rec.getProviderIncome());
        }
        if(ninth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && tenth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            ninth.setIncome(ninth.getIncome() + rec.getProviderIncome());
        }
        if(tenth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && eleventh.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            tenth.setIncome(tenth.getIncome() + rec.getProviderIncome());
        }
        if(eleventh.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0
                && twelfth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) > 0){
            eleventh.setIncome(eleventh.getIncome() + rec.getProviderIncome());
        }
        if(twelfth.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(rec.getDateOfEntry()) < 0){
            twelfth.setIncome(twelfth.getIncome() + rec.getProviderIncome());
        }
    }
}
