package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Boat extends SaleEntity {
   @Column
   private double length;
   @Column
   private int engineNumber;
   @Column
   private int enginePower;
   @Column
   private int maxSpeed;
   @Column
   private int maxPeople;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "user_id")
   private BoatOwner boatOwner;
   @OneToMany(mappedBy = "boat", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<BoatReservation> boatReservation;

   private BoatOwner getBoatOwner() {
      return boatOwner;
   }

   public double getLength() {
      return length;
   }

   public void setLength(double length) {
      this.length = length;
   }

   public int getEngineNumber() {
      return engineNumber;
   }

   public void setEngineNumber(int engineNumber) {
      this.engineNumber = engineNumber;
   }

   public int getEnginePower() {
      return enginePower;
   }

   public void setEnginePower(int enginePower) {
      this.enginePower = enginePower;
   }

   public int getMaxSpeed() {
      return maxSpeed;
   }

   public void setMaxSpeed(int maxSpeed) {
      this.maxSpeed = maxSpeed;
   }

   public int getMaxPeople() {
      return maxPeople;
   }

   public void setMaxPeople(int maxPeople) {
      this.maxPeople = maxPeople;
   }

   public void setBoatOwner(BoatOwner boatOwner) {
      this.boatOwner = boatOwner;
   }

   public List<BoatReservation> getBoatReservation() {
      return boatReservation;
   }

   public void setBoatReservation(List<BoatReservation> boatReservation) {
      this.boatReservation = boatReservation;
   }
}