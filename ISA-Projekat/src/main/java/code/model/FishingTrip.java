package code.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
   private Integer costPerDay;
   @Column
   private Integer percentageInstructorKeepsIfReservationCancelled;
   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name="location_id")
   @JsonManagedReference
   private Location location;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "fishingInstructor_id")
   @JsonBackReference
   private FishingInstructor fishingInstructor;
   @OneToMany(mappedBy = "fishingTrip", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference
   private Set<FishingTripPicture> pictures;
   @OneToMany(mappedBy = "fishingTrip", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JsonManagedReference
   private Set<FishingTripQuickReservation> fishingTripQuickReservations;
   @ElementCollection(targetClass= FishingTripReservationTag.class)
   @Enumerated(EnumType.ORDINAL)
   @CollectionTable(name="fishingTrip_tags")
   @Column(name="tags")
   @Fetch(value = FetchMode.JOIN)
   private Set<FishingTripReservationTag> fishingTripReservationTags;
}