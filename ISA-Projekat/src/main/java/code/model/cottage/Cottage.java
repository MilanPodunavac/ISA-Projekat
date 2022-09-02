package code.model.cottage;

import code.exceptions.entities.ClientCancelledThisPeriodException;
import code.exceptions.entities.InvalidReservationException;
import code.model.Client;
import code.model.Review;
import code.model.base.*;
import code.model.wrappers.DateRange;
import code.utils.FileUploadUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
   @JsonBackReference
   private CottageOwner cottageOwner;
   public boolean addReservation(CottageReservation reservation) throws InvalidReservationException, ClientCancelledThisPeriodException {
      for(CottageReservationTag tag : reservation.getCottageReservationTag()){
         if(!additionalServices.contains(tag))throw new InvalidReservationException("Additional service not supported");
      }
      if(reservation.getNumberOfPeople() > roomNumber * bedNumber) throw new InvalidReservationException("Not enough beds");
      AvailabilityPeriod periodToRemove = null;
      for(AvailabilityPeriod period : this.getAvailabilityPeriods()){
         if(period.getRange().includes(reservation.getDateRange())){
            periodToRemove = period;
            break;
         }
      }
      if(periodToRemove == null)throw new InvalidReservationException("Availability period not found");
      this.getAvailabilityPeriods().remove(periodToRemove);
      if(periodToRemove.getRange().getStartDate() != reservation.getDateRange().getStartDate()){
         AvailabilityPeriod newAvailabilityPeriod = new AvailabilityPeriod();
         newAvailabilityPeriod.setRange(new DateRange(periodToRemove.getRange().getStartDate(),reservation.getDateRange().getStartDate()));
         newAvailabilityPeriod.setSaleEntity(this);
         this.getAvailabilityPeriods().add(newAvailabilityPeriod);
      }
      if(periodToRemove.getRange().getEndDate() != reservation.getDateRange().getEndDate()){
         AvailabilityPeriod newAvailabilityPeriod = new AvailabilityPeriod();
         newAvailabilityPeriod.setRange(new DateRange(periodToRemove.getRange().getEndDate(),reservation.getDateRange().getEndDate()));
         newAvailabilityPeriod.setSaleEntity(this);
         this.getAvailabilityPeriods().add(newAvailabilityPeriod);
      }
      reservation.setCottage(this);
      reservation.setSystemCharge(reservation.getSystemCharge() - cottageOwner.getCategory().getLesserSystemTaxPercentage());
      //OVDE UVESTI DODATNO RACUNANJE POPUSTA ZA KLIJENTA I PROFITA ZA VLASNIKA
      return super.addReservation(reservation);
   }

   public boolean addAction(CottageAction newAction) throws InvalidReservationException {
      for(CottageReservationTag tag : newAction.getAdditionalServices()){
         if(!additionalServices.contains(tag))throw new InvalidReservationException("Additional service not supported");
      }
      if(newAction.getNumberOfPeople() > bedNumber * roomNumber)throw new InvalidReservationException("This cottage supports maximum " + bedNumber * roomNumber + " people");
      newAction.setCottage(this);
      newAction.setSystemCharge(newAction.getSystemCharge() - cottageOwner.getCategory().getLesserSystemTaxPercentage());
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
            if(act.getClient() != null)act.getClient().getActions().remove(act);
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
      for(Review rev : review){
         rev.setSaleEntity(null);
      }
      client.clear();
      review.clear();
      additionalServices.clear();
      cottageOwner.getCottage().remove(this);
      cottageOwner = null;
      location = null;
   }
}