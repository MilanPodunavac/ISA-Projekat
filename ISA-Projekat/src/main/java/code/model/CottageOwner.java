package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class CottageOwner extends User {
   @OneToMany(mappedBy = "cottageOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   public List<Cottage> cottage;

   public List<Cottage> getCottage() {
      return cottage;
   }

   public void setCottage(List<Cottage> cottage) {
      this.cottage = cottage;
   }
}