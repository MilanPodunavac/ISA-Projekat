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
   private Integer maxPeople;
   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name="location_id")
   private Location location;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "fishingInstructor_id")
   private FishingInstructor fishingInstructor;
}