package code.model;


import code.model.wrappers.DateRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AvailabilityPeriod {
    @Id
    @SequenceGenerator(name = "availabilityPeriodSeqGen", sequenceName = "availabilityPeriodSeq", initialValue = 50, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availabilityPeriodSeqGen")
    private int id;
    @Embedded
    private DateRange range;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "saleEntity_id")
    private SaleEntity saleEntity;
    @OneToMany(mappedBy = "availabilityPeriod", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

    public boolean isAvailable(DateRange range){
        if(!this.range.includes(range)) return false;
        for (Reservation reservation: reservations) {
            if(reservation.getDateRange().overlapsWith(range) && reservation.getReservationStatus() != ReservationStatus.cancelled) return false;
        }
        return true;
    }
    public boolean addReservation(Reservation reservation){
        if(isAvailable(reservation.getDateRange())){
            reservation.setAvailabilityPeriod(this);
            reservation.setReservationStatus(ReservationStatus.reserved);
            reservations.add(reservation);
            return true;
        }
        return false;
    }
}
