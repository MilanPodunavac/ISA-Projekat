package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class FishingTrip extends SaleEntity {
   @Column
   private int maxPeople;
   @ManyToOne
   @JoinColumn(name = "fishingInstructor_id")
   private FishingInstructor fishingInstructor;
   @OneToMany(mappedBy = "fishingTrip", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<FishingInstructorReservation> fishingInstructorReservation;

   public int getMaxPeople() {
      return maxPeople;
   }

   public void setMaxPeople(int maxPeople) {
      this.maxPeople = maxPeople;
   }

   public FishingInstructor getFishingInstructor() {
      return fishingInstructor;
   }

   public void setFishingInstructor(FishingInstructor fishingInstructor) {
      this.fishingInstructor = fishingInstructor;
   }

   public List<FishingInstructorReservation> getFishingInstructorReservation() {
      return fishingInstructorReservation;
   }

   public void setFishingInstructorReservation(List<FishingInstructorReservation> fishingInstructorReservation) {
      this.fishingInstructorReservation = fishingInstructorReservation;
   }
}