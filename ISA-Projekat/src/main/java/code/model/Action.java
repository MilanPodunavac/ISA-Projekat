package code.model;

import code.model.wrappers.DateRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Action {
    @Id
    @SequenceGenerator(name = "actionSeqGen", sequenceName = "actionSeq", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actionSeqGen")
    protected int id;
    @Embedded
    protected DateRange range;
    @Column
    protected Date validUntilAndIncluding;
    @Column
    protected int price;
    @Column
    protected double systemCharge;
    @Column
    protected double reservationRefund;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    protected Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "availabilityPeriod_id")
    protected AvailabilityPeriod availabilityPeriod;
    @Column
    protected boolean reserved;

    public boolean isValid(){
        return validUntilAndIncluding.after(new Date(System.currentTimeMillis()));
    }

    public boolean overlapsWith(DateRange range){
        return range.overlapsWith(range);
    }
}
