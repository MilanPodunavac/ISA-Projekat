package code.model;

import code.model.base.OwnerCommentary;
import code.model.base.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FishingTripReservation {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column
   private LocalDate start;
   @Column
   private Integer durationInDays;
   @Column
   private Integer numberOfPeople;
   @Column
   private Integer price;
   @ElementCollection(targetClass= FishingTripReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="fishingTripReservation_tags")
   @Column(name="tags")
   private Set<FishingTripReservationTag> fishingTripReservationTags;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "fishingTrip_id")
   private FishingTrip fishingTrip;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "client_id")
   private Client client;
   @Embedded
   protected OwnerCommentary ownerCommentary;
}