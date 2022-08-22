package code.model.cottage;

import code.exceptions.entities.ClientCancelledThisPeriodException;
import code.exceptions.entities.InvalidReservationException;
import code.model.Client;
import code.model.Review;
import code.model.base.*;
import code.utils.FileUploadUtil;
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
public class Cottage extends SaleEntity {
   @Column
   private int roomNumber;
   @Column
   private int bedNumber;
   @ElementCollection(targetClass=CottageReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="cottageAdditionalServices")
   @Column(name="tags")
   @Fetch(value = FetchMode.JOIN)
   private Set<CottageReservationTag> additionalServices;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "cottageOwner_id")
   private CottageOwner cottageOwner;
   public boolean addReservation(CottageReservation reservation) throws InvalidReservationException, ClientCancelledThisPeriodException {
      for(CottageReservationTag tag : reservation.getCottageReservationTag()){
         if(!additionalServices.contains(tag))throw new InvalidReservationException("Additional service not supported");
      }
      if(reservation.getNumberOfPeople() > roomNumber * bedNumber) throw new InvalidReservationException("Not enough beds");
      //OVDE UVESTI DODATNO RACUNANJE POPUSTA ZA KLIJENTA I PROFITA ZA VLASNIKA
      return super.addReservation(reservation);
   }

   public boolean addAction(CottageAction newAction) throws InvalidReservationException {
      for(CottageReservationTag tag : newAction.getAdditionalServices()){
         if(!additionalServices.contains(tag))throw new InvalidReservationException("Additional service not supported");
      }
      if(newAction.getNumberOfPeople() > bedNumber * roomNumber)throw new InvalidReservationException("This cottage supports maximum " + bedNumber * roomNumber + " people");
      newAction.setCottage(this);
      return super.addAction(newAction);
   }

   @PreRemove
   private void removeReferences(){
      for(AvailabilityPeriod period:availabilityPeriods){
         for(Reservation res:period.getReservations()){
            res.getClient().getReservation().remove(res);
            ((CottageReservation)res).setCottage(null);
            ((CottageReservation)res).getCottageReservationTag().clear();
            res.setClient(null);
            res.setAvailabilityPeriod(null);
         }
         for(Action act : period.getActions()){
            act.getClient().getActions().remove(act);
            ((CottageAction)act).setCottage(null);
            ((CottageAction)act).getAdditionalServices().clear();
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
      cottageOwner.getCottage().remove(this);
      cottageOwner = null;
      location = null;
   }
}