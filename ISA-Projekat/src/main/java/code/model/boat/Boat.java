package code.model.boat;

import code.exceptions.entities.ClientCancelledThisPeriodException;
import code.exceptions.entities.InvalidReservationException;
import code.model.Client;
import code.model.Review;
import code.model.base.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Boat extends SaleEntity {
   @Column
   private double length;
   @Column
   private String type;
   @Column
   private int engineNumber;
   @Column
   private int enginePower;
   @Column
   private int maxSpeed;
   @Column
   private int maxPeople;
   @Column
   private NavigationalEquipment navigationalEquipment;
   @Column
   private String fishingEquipment;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "user_id")
   private BoatOwner boatOwner;
   @ElementCollection(targetClass= BoatReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="boatAdditionalServices")
   @Column(name="tags")
   @Fetch(value = FetchMode.JOIN)
   private Set<BoatReservationTag> additionalServices;

   public boolean addReservation(BoatReservation reservation) throws ClientCancelledThisPeriodException, InvalidReservationException {
      for(BoatReservationTag tag : reservation.getBoatReservationTag()){
         if(!additionalServices.contains(tag))throw new InvalidReservationException("Additional service not supported");
      }
      if(reservation.getNumberOfPeople() > maxPeople) throw new InvalidReservationException("This boat supports maximum " + maxPeople + " people");
      reservation.setBoat(this);
      return super.addReservation(reservation);
   }

   public boolean addAction(BoatAction newAction) throws InvalidReservationException {
      for(BoatReservationTag tag : newAction.getAdditionalServices()){
         if(!additionalServices.contains(tag))throw new InvalidReservationException("Additional service not supported");
      }
      if(newAction.getNumberOfPeople() > maxPeople)throw new InvalidReservationException("This boat supports maximum " + maxPeople + " people");
      newAction.setBoat(this);
      return super.addAction(newAction);
   }

   @PreRemove
   private void removeReferences(){
      for(AvailabilityPeriod period:availabilityPeriods){
         for(Reservation res:period.getReservations()){
            res.getClient().getReservation().remove(res);
            ((BoatReservation)res).setBoat(null);
            ((BoatReservation)res).getBoatReservationTag().clear();
            res.setClient(null);
            res.setAvailabilityPeriod(null);
         }
         for(Action act : period.getActions()){
            act.getClient().getActions().remove(act);
            ((BoatAction)act).setBoat(null);
            ((BoatAction)act).getAdditionalServices().clear();
            act.setClient(null);
            act.setAvailabilityPeriod(null);
         }
         period.getReservations().clear();
         period.getActions().clear();
         period.setSaleEntity(null);
      }
      availabilityPeriods.clear();
      for(Picture pic : pictures){
         pic.setSaleEntity(null);
      }
      pictures.clear();
      for(Review review : review){
         review.getClient().getReview().remove(review);
         review.setClient(null);
         review.setSaleEntity(null);
      }
      for(Client client : client){
         client.getSaleEntity().remove(this);
      }
      client.clear();
      review.clear();
      additionalServices.clear();
      boatOwner.getBoat().remove(this);
      boatOwner = null;
      location = null;
   }
}