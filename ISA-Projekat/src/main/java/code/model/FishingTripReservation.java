package code.model;

import code.model.base.Reservation;
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
public class FishingTripReservation extends Reservation {
   @ElementCollection(targetClass= FishingTripReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="fishingTripReservation_tags")
   @Column(name="tags")
   private Set<FishingTripReservationTag> fishingTripReservationTag;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "fishingTrip_id")
   private FishingTrip fishingTrip;
}