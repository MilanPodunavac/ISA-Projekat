package code.model;

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
public class FishingInstructor extends User {
   @Column
   private String reasonForRegistration;
   @Column
   private String biography;
   @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JsonManagedReference
   private Set<FishingTrip> fishingTrips;
   @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JsonManagedReference
   private Set<FishingInstructorAvailablePeriod> fishingInstructorAvailablePeriods;
   @ManyToMany(mappedBy = "instructorsSubscribedTo")
   @JsonManagedReference
   @Fetch(value = FetchMode.JOIN)
   private Set<Client> subscribers;
}