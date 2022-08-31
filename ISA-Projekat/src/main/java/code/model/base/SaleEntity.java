package code.model.base;

import code.exceptions.entities.AvailabilityPeriodBadRangeException;
import code.exceptions.entities.ClientCancelledThisPeriodException;
import code.model.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
   @Column
   protected int reservationRefund;
   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name="location_id")
   @JsonManagedReference
   protected Location location;
   @ManyToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER)
   @JsonBackReference
   protected Set<Client> client;
   @OneToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference
   protected Set<Review> review;
   @OneToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference
   protected Set<AvailabilityPeriod> availabilityPeriods;
   @OneToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference
   protected Set<Picture> pictures;

   @Column
   protected int scheduleChanged;

   @Version
   protected int version;



   public void addAvailabilityPeriod (AvailabilityPeriod period) throws AvailabilityPeriodBadRangeException {
      for(AvailabilityPeriod existingPeriods: availabilityPeriods){
         if(period.getRange().overlapsWith(existingPeriods.getRange())){ throw new AvailabilityPeriodBadRangeException("This period is already available");}
      }
      period.setSaleEntity(this);
      availabilityPeriods.add(period);
      scheduleChanged += 1;
   }

   public boolean addReservation (Reservation reservation) throws ClientCancelledThisPeriodException {
      reservation.setPrice((pricePerDay * reservation.getDateRange().durationInDays()) * (1 - reservation.getClient().getCategory().getDiscountPercentage()/100));
      reservation.setReservationRefund(reservationRefund);
      for (AvailabilityPeriod period: availabilityPeriods) {
         if(period.addReservation(reservation) == true) {
            scheduleChanged += 1;
            return true;
         }
      }
      return false;
   }

   public boolean addAction (Action action){
      action.setPrice((int) ((action.getRange().durationInDays() * pricePerDay)*(1 - ((double)action.getDiscount()/100))));
      action.setActionRefund(reservationRefund);
      for(AvailabilityPeriod period : availabilityPeriods){
         if(period.addAction(action) == true){
            scheduleChanged += 1;
            return true;
         }
      }
      return false;
   }

   public void addPicture(Picture picture){
      picture.setSaleEntity(this);
      pictures.add(picture);
   }

   public boolean hasFutureReservationsOrActions(){
      for(AvailabilityPeriod period : availabilityPeriods){
         for(Reservation reservation : period.getReservations()){
            if(System.currentTimeMillis() < reservation.getDateRange().getStartDate().getTime())return true;
         }
         for(Action action : period.getActions()){
            if(System.currentTimeMillis() < action.getRange().getStartDate().getTime())return true;
         }
      }
      return false;
   }

   public double calculateAverageGrade(){
      if(review == null || review.size() == 0)return -1;
      double sum = 0;
      for(Review rev: review){
         sum += rev.getGrade();
      }
      return sum/review.size();
   }
}