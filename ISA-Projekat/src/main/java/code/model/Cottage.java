package code.model;

import code.exceptions.entities.InvalidReservationException;
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
public class Cottage extends SaleEntity {
   @Column
   private int roomNumber;
   @Column
   private int bedNumber;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "cottageOwner_id")
   private CottageOwner cottageOwner;
   //@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   //private Set<CottageReservation> cottageReservation;
   public boolean addReservation(CottageReservation reservation) throws InvalidReservationException {
      if(reservation.getNumberOfPeople() > roomNumber * bedNumber) throw new InvalidReservationException("Not enough beds");
      return super.addReservation(reservation);
   }
}