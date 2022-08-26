package code.model.boat;

import code.model.base.SaleEntity;
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
   @JsonBackReference
   private BoatOwner boatOwner;
   @ElementCollection(targetClass= BoatReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="boatAdditionalServices")
   @Column(name="tags")
   @Fetch(value = FetchMode.JOIN)
   private Set<BoatReservationTag> additionalServices;
}