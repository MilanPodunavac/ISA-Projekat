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
public class FishingTrip extends SaleEntity {
   @Column
   private int maxPeople;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "fishingInstructor_id")
   private FishingInstructor fishingInstructor;
   @OneToMany(mappedBy = "fishingTrip", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<FishingInstructorReservation> fishingInstructorReservation;

}