package code.model.boat;

import code.model.LoyaltyProgramProvider;
import code.model.User;
import code.model.boat.Boat;
import code.model.cottage.Cottage;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
   @JsonManagedReference
   private Set<Boat> boat;
   @Column
   private double loyaltyPoints;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name="category_id")
   private LoyaltyProgramProvider category;

   public void addBoat(Boat boat){
      boat.setBoatOwner(this);
      this.boat.add(boat);
   }
}