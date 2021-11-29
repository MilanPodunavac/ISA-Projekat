package code.model;

import javax.persistence.*;
import java.util.*;

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

   public List<FishingInstructorReservationTag> getFishingInstructorReservationTag() {
      return fishingInstructorReservationTag;
   }

   public void setFishingInstructorReservationTag(List<FishingInstructorReservationTag> fishingInstructorReservationTag) {
      this.fishingInstructorReservationTag = fishingInstructorReservationTag;
   }

   public FishingTrip getFishingTrip() {
      return fishingTrip;
   }

   public void setFishingTrip(FishingTrip fishingTrip) {
      this.fishingTrip = fishingTrip;
   }
}