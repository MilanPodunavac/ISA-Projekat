package code.model.boat;

import code.model.User;
import code.model.boat.Boat;
import code.model.cottage.Cottage;
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
public class BoatOwner extends User {
   @Column
   private String reasonForRegistration;
   @OneToMany(mappedBy = "boatOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<Boat> boat;

   public void addBoat(Boat boat){
      boat.setBoatOwner(this);
      this.boat.add(boat);
   }
}