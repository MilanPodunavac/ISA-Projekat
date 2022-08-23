package code.model.cottage;

import code.model.LoyaltyProgramProvider;
import code.model.User;
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
   @Column
   private double loyaltyPoints;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name="category_id")
   private LoyaltyProgramProvider category;

   public void addCottage(Cottage cottage){
      cottage.setCottageOwner(this);
      this.cottage.add(cottage);
   }
}