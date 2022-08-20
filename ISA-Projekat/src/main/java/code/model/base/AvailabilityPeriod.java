package code.model.base;


import code.exceptions.entities.ClientCancelledThisPeriodException;
import code.model.wrappers.DateRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @OneToMany(mappedBy = "availabilityPeriod", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations;
    @OneToMany(mappedBy = "availabilityPeriod", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Action> actions;

    public boolean isAvailable(DateRange range){
        if(!this.range.includes(range)) return false;
        for (Reservation reservation: reservations) {
            if(reservation.getDateRange().overlapsWith(range) && reservation.getReservationStatus() != ReservationStatus.cancelled) return false;
        }
        for (Action action : actions){
            if(action.overlapsWith(range)) return false;
        }
        return true;
    }
    public boolean addReservation(Reservation reservation) throws ClientCancelledThisPeriodException {
        if(isAvailable(reservation.getDateRange())){
            for(Reservation res : reservations){
                if(res.getReservationStatus() == ReservationStatus.cancelled && res.getClient().getId().intValue() == reservation.getClient().getId().intValue() && res.getDateRange().overlapsWith(reservation.getDateRange())){
                    throw new ClientCancelledThisPeriodException("Client cancelled this period before");
                }
            }
            reservation.setAvailabilityPeriod(this);
            reservation.setReservationStatus(ReservationStatus.reserved);
            reservations.add(reservation);
            return true;
        }
        return false;
    }

    public boolean addAction(Action action){
        if(isAvailable(action.getRange())){
            action.setAvailabilityPeriod(this);
            action.setReserved(false);
            actions.add(action);
            return true;
        }
        return false;
    }
}
