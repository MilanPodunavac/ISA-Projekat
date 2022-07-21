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
public class CottageReservation extends Reservation {
   @ElementCollection(targetClass=CottageReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="cottageReservation_tags")
   @Column(name="tags")
   private List<CottageReservationTag> cottageReservationTag;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "cottage_id")
   private Cottage cottage;

}