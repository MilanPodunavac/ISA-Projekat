package code.model.cottage;

import code.exceptions.entities.InvalidReservationException;
import code.model.SaleEntity;
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
   public boolean addReservation(CottageReservation reservation) throws InvalidReservationException {
      for(CottageReservationTag tag : reservation.getCottageReservationTag()){
         if(!additionalServices.contains(tag))throw new InvalidReservationException("Additional service not supported");
      }
      if(reservation.getNumberOfPeople() > roomNumber * bedNumber) throw new InvalidReservationException("Not enough beds");
      return super.addReservation(reservation);
   }

   public boolean addAction(CottageAction newAction){
      newAction.setCottage(this);
      super.addAction(newAction);
      return true;
   }
}