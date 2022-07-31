package code.model;


import code.model.wrappers.DateRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AvailabilityPeriod {
    @Id
    @SequenceGenerator(name = "availabilityPeriodSeqGen", sequenceName = "availabilityPeriodSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "availabilityPeriodSeqGen")
    private int id;
    @Embedded
    private DateRange range;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "saleEntity_id")
    private SaleEntity saleEntity;
}
