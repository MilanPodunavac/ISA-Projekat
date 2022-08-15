package code.model;

import code.model.base.Action;
import code.model.base.Reservation;
import code.model.base.SaleEntity;
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
   private Set<Reservation> reservation;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<FishingTripQuickReservation> fishingTripQuickReservations;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<Action> actions;
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "client_saleEntity", joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "saleEntity_id", referencedColumnName = "id"))
   private Set<SaleEntity> saleEntity;
   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<Review> review;
}