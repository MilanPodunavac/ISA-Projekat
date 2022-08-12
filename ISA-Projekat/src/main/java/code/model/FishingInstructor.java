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
public class FishingInstructor extends User {
   @Column
   private String reasonForRegistration;
   @Column
   private String biography;
   @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<FishingTrip> fishingTrips;
   @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<FishingInstructorAvailablePeriod> fishingInstructorAvailablePeriods;
}