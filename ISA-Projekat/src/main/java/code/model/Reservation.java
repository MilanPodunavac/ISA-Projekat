package code.model;

import code.model.wrappers.DateRange;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Reservation {
   @Id
   @SequenceGenerator(name = "reservationSeqGen", sequenceName = "reservationSeq", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservationSeqGen")
   protected Integer id;
   @Embedded
   protected DateRange dateRange;
   @Column
   protected int maxPeople;
   @Column
   protected double price;
   @Column
   protected double reservationRefund;
   @Column
   protected double systemCharge;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "client_id")
   protected Client client;
   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "reservationDiscount_id")
   protected Set<ReservationDiscount> reservationDiscount;
   @Enumerated(EnumType.ORDINAL)
   protected ReservationStatus reservationStatus;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "availabilityPeriod_id")
   protected AvailabilityPeriod availabilityPeriod;

}