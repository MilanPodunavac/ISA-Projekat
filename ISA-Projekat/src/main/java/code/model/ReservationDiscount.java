package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class ReservationDiscount {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "reservationDiscount_id")
   private Integer Id;
   @Column
   private double discount;
   @Column
   private Date expiringDate;

   public double getDiscount() {
      return discount;
   }

   public void setDiscount(double discount) {
      this.discount = discount;
   }

   public Date getExpiringDate() {
      return expiringDate;
   }

   public void setExpiringDate(Date expiringDate) {
      this.expiringDate = expiringDate;
   }
}