package code.model;

import code.model.base.SaleEntity;
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
public class Boat extends SaleEntity {
   @Column
   private double length;
   @Column
   private int engineNumber;
   @Column
   private int enginePower;
   @Column
   private int maxSpeed;
   @Column
   private int maxPeople;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "user_id")
   private BoatOwner boatOwner;
   @OneToMany(mappedBy = "boat", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<BoatReservation> boatReservation;
}