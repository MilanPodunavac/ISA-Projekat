/***********************************************************************
 * Module:  BoatReservation.java
 * Author:  User
 * Purpose: Defines the Class BoatReservation
 ***********************************************************************/

import java.util.*;

/** @pdOid e9821043-721a-480f-8b29-264bd4ebae5e */
public class BoatReservation extends Reservation {
   /** @pdRoleInfo migr=no name=BoatReservationTag assc=association15 coll=java.util.List impl=java.util.ArrayList mult=0..* type=Composition */
   public java.util.List<BoatReservationTag> boatReservationTag;
   /** @pdRoleInfo migr=no name=Boat assc=association9 mult=0..1 side=A */
   public Boat boat;
   
   
   /** @pdGenerated default getter */
   public java.util.List<BoatReservationTag> getBoatReservationTag() {
      if (boatReservationTag == null)
         boatReservationTag = new java.util.ArrayList<BoatReservationTag>();
      return boatReservationTag;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorBoatReservationTag() {
      if (boatReservationTag == null)
         boatReservationTag = new java.util.ArrayList<BoatReservationTag>();
      return boatReservationTag.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newBoatReservationTag */
   public void setBoatReservationTag(java.util.List<BoatReservationTag> newBoatReservationTag) {
      removeAllBoatReservationTag();
      for (java.util.Iterator iter = newBoatReservationTag.iterator(); iter.hasNext();)
         addBoatReservationTag((BoatReservationTag)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newBoatReservationTag */
   public void addBoatReservationTag(BoatReservationTag newBoatReservationTag) {
      if (newBoatReservationTag == null)
         return;
      if (this.boatReservationTag == null)
         this.boatReservationTag = new java.util.ArrayList<BoatReservationTag>();
      if (!this.boatReservationTag.contains(newBoatReservationTag))
         this.boatReservationTag.add(newBoatReservationTag);
   }
   
   /** @pdGenerated default remove
     * @param oldBoatReservationTag */
   public void removeBoatReservationTag(BoatReservationTag oldBoatReservationTag) {
      if (oldBoatReservationTag == null)
         return;
      if (this.boatReservationTag != null)
         if (this.boatReservationTag.contains(oldBoatReservationTag))
            this.boatReservationTag.remove(oldBoatReservationTag);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllBoatReservationTag() {
      if (boatReservationTag != null)
         boatReservationTag.clear();
   }
   /** @pdGenerated default parent getter */
   public Boat getBoat() {
      return boat;
   }
   
   /** @pdGenerated default parent setter
     * @param newBoat */
   public void setBoat(Boat newBoat) {
      if (this.boat == null || !this.boat.equals(newBoat))
      {
         if (this.boat != null)
         {
            Boat oldBoat = this.boat;
            this.boat = null;
            oldBoat.removeBoatReservation(this);
         }
         if (newBoat != null)
         {
            this.boat = newBoat;
            this.boat.addBoatReservation(this);
         }
      }
   }

}