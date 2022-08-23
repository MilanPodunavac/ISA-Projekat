package code.model;

import code.model.base.Action;
import code.model.base.Reservation;
import code.model.base.ReservationStatus;
import code.model.base.SaleEntity;
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
public class Client extends User {
   @Column
   private int penaltyPoints;
   @Column
   private boolean banned;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<Reservation> reservation;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<FishingTripQuickReservation> fishingTripQuickReservations;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<FishingTripReservation> fishingTripReservations;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<Action> actions;
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "client_saleEntity", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "saleEntity_id", referencedColumnName = "id"))
   private Set<SaleEntity> saleEntity;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<Review> review;
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "subscriber_fishing_instructor", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "instructor_id", referencedColumnName = "id"))
   private Set<FishingInstructor> instructorsSubscribedTo;
   @Column
   private double loyaltyPoints;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name="category_id")
   private LoyaltyProgramClient category;

   public boolean isAvailable(DateRange range){
      if(reservation == null)return true;
      for (Reservation res: reservation) {
         if(res.getDateRange().overlapsWith(range) && res.getReservationStatus() == ReservationStatus.reserved)return false;
      }
      if(actions == null)return true;
      for (Action act: actions){
         if(act.overlapsWith(range) && !act.isReserved())return false;
      }
      return true;
   }
}