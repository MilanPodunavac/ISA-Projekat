package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Reservation {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   protected Integer id;
   @Column
   protected Date startDate;
   @Column
   protected Date endDate;
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
   protected Collection<ReservationDiscount> reservationDiscount;
   @Enumerated(EnumType.ORDINAL)
   protected ReservationStatus reservationStatus;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Date getStartDate() {
      return startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   public Date getEndDate() {
      return endDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   public int getMaxPeople() {
      return maxPeople;
   }

   public void setMaxPeople(int maxPeople) {
      this.maxPeople = maxPeople;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public double getReservationRefund() {
      return reservationRefund;
   }

   public void setReservationRefund(double reservationRefund) {
      this.reservationRefund = reservationRefund;
   }

   public double getSystemCharge() {
      return systemCharge;
   }

   public void setSystemCharge(double systemCharge) {
      this.systemCharge = systemCharge;
   }

   public Client getClient() {
      return client;
   }

   public void setClient(Client client) {
      this.client = client;
   }

   public Collection<ReservationDiscount> getReservationDiscount() {
      return reservationDiscount;
   }

   public void setReservationDiscount(Collection<ReservationDiscount> reservationDiscount) {
      this.reservationDiscount = reservationDiscount;
   }

   public ReservationStatus getReservationStatus() {
      return reservationStatus;
   }

   public void setReservationStatus(ReservationStatus reservationStatus) {
      this.reservationStatus = reservationStatus;
   }
}