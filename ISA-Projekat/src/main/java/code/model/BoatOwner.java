package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BoatOwner extends User {
   @OneToMany(mappedBy = "boatOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<Boat> boat;
}