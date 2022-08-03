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
   @Column
   private String reasonForRegistration;
   @OneToMany(mappedBy = "cottageOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<Cottage> cottage;

   public void addCottage(Cottage cottage){
      cottage.setCottageOwner(this);
      this.cottage.add(cottage);
   }
}