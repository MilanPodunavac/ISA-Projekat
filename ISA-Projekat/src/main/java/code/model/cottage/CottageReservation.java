package code.model.cottage;

import code.model.Reservation;
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
public class CottageReservation extends Reservation {
   @ElementCollection(targetClass=CottageReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="cottageReservation_tags")
   @Column(name="tags")
   @Fetch(value = FetchMode.JOIN)
   private Set<CottageReservationTag> cottageReservationTag;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "cottage_id")
   private Cottage cottage;

}