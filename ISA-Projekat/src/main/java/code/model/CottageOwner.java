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
public class CottageOwner extends User {
   @OneToMany(mappedBy = "cottageOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<Cottage> cottage;
}