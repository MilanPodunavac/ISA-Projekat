package code.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class BoatOwner extends User {
   @OneToMany(mappedBy = "boatOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<Boat> boat;

   public List<Boat> getBoat() {
      return boat;
   }

   public void setBoat(List<Boat> boat) {
      this.boat = boat;
   }
}