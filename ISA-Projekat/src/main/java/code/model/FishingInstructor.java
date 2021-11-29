package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class FishingInstructor extends User {
   @Column
   private String biography;
   @OneToMany(mappedBy = "fishingInstructor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<FishingTrip> fishingTrip;

   public String getBiography() {
      return biography;
   }

   public void setBiography(String biography) {
      this.biography = biography;
   }

   public List<FishingTrip> getFishingTrip() {
      return fishingTrip;
   }

   public void setFishingTrip(List<FishingTrip> fishingTrip) {
      this.fishingTrip = fishingTrip;
   }
}