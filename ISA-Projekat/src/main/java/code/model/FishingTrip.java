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
public class FishingTrip {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column
   private String name;
   @Column
   private String description;
   @Column
   private String rules;
   @Column
   private String equipment;
   @Column
   private Integer maxPeople;
   @Column
   private double costPerDay;
   @Column
   private double percentageInstructorKeepsIfReservationCancelled;
   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name="location_id")
   private Location location;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "fishingInstructor_id")
   private FishingInstructor fishingInstructor;
   @OneToMany(mappedBy = "fishingTrip", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<FishingTripPicture> pictures;
   @OneToMany(mappedBy = "fishingTrip", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<FishingTripQuickReservation> fishingTripQuickReservations;
   @ElementCollection(targetClass= FishingTripReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="fishingTrip_tags")
   @Column(name="tags")
   private Set<FishingTripReservationTag> fishingTripReservationTags;
}