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
public class FishingInstructorReservation extends Reservation {
   @ElementCollection(targetClass=FishingInstructorReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="fishingInstructorReservation_tags")
   @Column(name="tags")
   private List<FishingInstructorReservationTag> fishingInstructorReservationTag;
   @ManyToOne
   @JoinColumn(name = "fishingTrip_id")
   private FishingTrip fishingTrip;
}