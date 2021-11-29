package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class BoatReservation extends Reservation {
   @ElementCollection(targetClass=BoatReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="boatReservation_tags")
   @Column(name="tags")
   private List<BoatReservationTag> boatReservationTag;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "boat_id")
   private Boat boat;

   public List<BoatReservationTag> getBoatReservationTag() {
      return boatReservationTag;
   }

   public void setBoatReservationTag(List<BoatReservationTag> boatReservationTag) {
      this.boatReservationTag = boatReservationTag;
   }

   public Boat getBoat() {
      return boat;
   }

   public void setBoat(Boat boat) {
      this.boat = boat;
   }
}