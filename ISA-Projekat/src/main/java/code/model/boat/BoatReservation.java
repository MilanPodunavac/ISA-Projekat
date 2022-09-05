package code.model.boat;

import code.model.base.Reservation;
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
public class BoatReservation extends Reservation {
   @ElementCollection(targetClass= BoatReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="boatReservation_tags")
   @Column(name="tags")
   @Fetch(value = FetchMode.JOIN)
   private Set<BoatReservationTag> boatReservationTag;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "boat_id")
   @JsonBackReference
   private Boat boat;
   @Column
   private boolean  ownerNeeded;
}