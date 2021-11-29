package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class CottageReservation extends Reservation {
   @ElementCollection(targetClass=CottageReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="cottageReservation_tags")
   @Column(name="tags")
   private List<CottageReservationTag> cottageReservationTag;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "cottage_id")
   private Cottage cottage;

   public List<CottageReservationTag> getCottageReservationTag() {
      return cottageReservationTag;
   }

   public void setCottageReservationTag(List<CottageReservationTag> cottageReservationTag) {
      this.cottageReservationTag = cottageReservationTag;
   }

   public Cottage getCottage() {
      return cottage;
   }

   public void setCottage(Cottage cottage) {
      this.cottage = cottage;
   }

}