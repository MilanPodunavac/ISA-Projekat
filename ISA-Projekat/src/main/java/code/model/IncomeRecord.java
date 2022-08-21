package code.model;

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
@Entity
public class IncomeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private boolean reserved;
    @Column
    private LocalDate dateOfEntry;
    @Column
    private LocalDate reservationStart;
    @Column
    private LocalDate reservationEnd;
    @Column
    private double reservationPrice;
    @Column
    private double systemTaxPercentage;
    @Column
    private double percentageOwnerKeepsIfReservationCancelled;
    @Column
    private double systemIncome;
    @Column
    private double ownerIncome;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_owner_id")
    private User reservationOwner;
}
