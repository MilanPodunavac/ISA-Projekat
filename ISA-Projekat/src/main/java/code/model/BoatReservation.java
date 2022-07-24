package code.model;

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
public class BoatReservation extends Reservation {
   @ElementCollection(targetClass=BoatReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="boatReservation_tags")
   @Column(name="tags")
   private Set<BoatReservationTag> boatReservationTag;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "boat_id")
   private Boat boat;
}