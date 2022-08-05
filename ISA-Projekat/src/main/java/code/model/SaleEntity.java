package code.model;

import code.exceptions.entities.AvailabilityPeriodBadRangeException;
import code.model.wrappers.DateRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SaleEntity {
   @Id
   @SequenceGenerator(name = "saleEntitySeqGen", sequenceName = "saleEntitySeq", initialValue = 1000, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "saleEntitySeqGen")
   protected Integer id;
   @Column
   protected String name;
   @Column
   protected String description;
   @Column
   protected String rules;
   @Column
   protected int pricePerDay;
   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name="location_id")
   protected Location location;
   @ManyToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER)
   protected Set<Client> client;
   @OneToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   protected Set<Review> review;
   @OneToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   protected Set<AvailabilityPeriod> availabilityPeriods;

   public void addAvailabilityPeriod (AvailabilityPeriod period) throws AvailabilityPeriodBadRangeException {
      for(AvailabilityPeriod existingPeriods: availabilityPeriods){
         if(period.getRange().overlapsWith(existingPeriods.getRange())){ throw new AvailabilityPeriodBadRangeException("This period is already available");}
      }
      period.setSaleEntity(this);
      availabilityPeriods.add(period);
   }

   public boolean addReservation (Reservation reservation){
      reservation.setPrice(pricePerDay * reservation.getDateRange().getDays());
      for (AvailabilityPeriod period: availabilityPeriods) {
         if(period.addReservation(reservation) == true) return true;
      }
      return false;
   }

}