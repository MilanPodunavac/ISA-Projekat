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
public class ReservationDiscount {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "reservationDiscount_id")
   private Integer Id;
   @Column
   private double discount;
   @Column
   private Date expiringDate;

}