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
public class BoatOwner extends User {
   @Column
   private String reasonForRegistration;
   @OneToMany(mappedBy = "boatOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<Boat> boat;
}