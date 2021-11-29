package code.model;

import javax.persistence.*;
import java.util.*;

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

   public int getPenaltyPoints() {
      return penaltyPoints;
   }

   public void setPenaltyPoints(int penaltyPoints) {
      this.penaltyPoints = penaltyPoints;
   }

   public boolean isBanned() {
      return banned;
   }

   public void setBanned(boolean banned) {
      this.banned = banned;
   }

   public List<Reservation> getReservation() {
      return reservation;
   }

   public void setReservation(List<Reservation> reservation) {
      this.reservation = reservation;
   }

   public List<SaleEntity> getSaleEntity() {
      return saleEntity;
   }

   public void setSaleEntity(List<SaleEntity> saleEntity) {
      this.saleEntity = saleEntity;
   }

   public List<Review> getReview() {
      return review;
   }

   public void setReview(List<Review> review) {
      this.review = review;
   }
}