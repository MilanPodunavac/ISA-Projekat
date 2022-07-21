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
public class Client extends User {
   @Column
   private int penaltyPoints;
   @Column
   private boolean banned;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<Reservation> reservation;
   @ManyToMany
   @JoinTable(name = "client_saleEntity", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "saleEntity_id", referencedColumnName = "id"))
   private List<SaleEntity> saleEntity;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<Review> review;
}