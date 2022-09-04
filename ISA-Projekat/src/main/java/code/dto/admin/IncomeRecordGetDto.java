package code.dto.admin;

import code.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeRecordGetDto {
    private Integer id;
    private boolean reserved;
    private LocalDate dateOfEntry;
    private LocalDate reservationStart;
    private LocalDate reservationEnd;
    private double reservationPrice;
    private double systemTaxPercentage;
    private double percentageProviderKeepsIfReservationCancelled;
    private double systemIncome;
    private double providerIncome;
    private User reservationProvider;
}
