package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cottage extends SaleEntity {
   @Column
   private int roomNumber;
   @Column
   private int bedNumber;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "cottageOwner_id")
   private CottageOwner cottageOwner;
   @OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<CottageReservation> cottageReservation;

   public int getRoomNumber() {
      return roomNumber;
   }

   public void setRoomNumber(int roomNumber) {
      this.roomNumber = roomNumber;
   }

   public int getBedNumber() {
      return bedNumber;
   }

   public void setBedNumber(int bedNumber) {
      this.bedNumber = bedNumber;
   }

   public CottageOwner getCottageOwner() {
      return cottageOwner;
   }

   public void setCottageOwner(CottageOwner cottageOwner) {
      this.cottageOwner = cottageOwner;
   }

   public List<CottageReservation> getCottageReservation() {
      return cottageReservation;
   }

   public void setCottageReservation(List<CottageReservation> cottageReservation) {
      this.cottageReservation = cottageReservation;
   }
}