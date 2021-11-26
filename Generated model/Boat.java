/***********************************************************************
 * Module:  Boat.java
 * Author:  User
 * Purpose: Defines the Class Boat
 ***********************************************************************/

import java.util.*;

/** @pdOid 879c2790-5df2-4a9f-9e72-caf1097f5cbb */
public class Boat extends SaleEntity {
   /** @pdOid 0b79161e-5ef4-4a9f-8631-031d483e1e93 */
   private double length;
   /** @pdOid 1b53e7c7-584f-4a89-9ad7-2ede4897af34 */
   private int engineNumber;
   /** @pdOid 6cebb5a0-f5b8-4226-990a-e52675768732 */
   private int enginePower;
   /** @pdOid 758e6914-7c24-4329-8b6a-38c3679c9ec2 */
   private int maxSpeed;
   /** @pdOid 29c72d89-ae8b-41de-bfd1-732fc2d3632c */
   private int maxPeople;
   
   /** @pdRoleInfo migr=no name=BoatOwner assc=association7 mult=0..1 */
   public BoatOwner boatOwner;
   /** @pdRoleInfo migr=no name=BoatReservation assc=association9 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   public java.util.List<BoatReservation> boatReservation;
   
   
   /** @pdGenerated default parent getter */
   public BoatOwner getBoatOwner() {
      return boatOwner;
   }
   
   /** @pdGenerated default parent setter
     * @param newBoatOwner */
   public void setBoatOwner(BoatOwner newBoatOwner) {
      if (this.boatOwner == null || !this.boatOwner.equals(newBoatOwner))
      {
         if (this.boatOwner != null)
         {
            BoatOwner oldBoatOwner = this.boatOwner;
            this.boatOwner = null;
            oldBoatOwner.removeBoat(this);
         }
         if (newBoatOwner != null)
         {
            this.boatOwner = newBoatOwner;
            this.boatOwner.addBoat(this);
         }
      }
   }
   /** @pdGenerated default getter */
   public java.util.List<BoatReservation> getBoatReservation() {
      if (boatReservation == null)
         boatReservation = new java.util.ArrayList<BoatReservation>();
      return boatReservation;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorBoatReservation() {
      if (boatReservation == null)
         boatReservation = new java.util.ArrayList<BoatReservation>();
      return boatReservation.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newBoatReservation */
   public void setBoatReservation(java.util.List<BoatReservation> newBoatReservation) {
      removeAllBoatReservation();
      for (java.util.Iterator iter = newBoatReservation.iterator(); iter.hasNext();)
         addBoatReservation((BoatReservation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newBoatReservation */
   public void addBoatReservation(BoatReservation newBoatReservation) {
      if (newBoatReservation == null)
         return;
      if (this.boatReservation == null)
         this.boatReservation = new java.util.ArrayList<BoatReservation>();
      if (!this.boatReservation.contains(newBoatReservation))
      {
         this.boatReservation.add(newBoatReservation);
         newBoatReservation.setBoat(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldBoatReservation */
   public void removeBoatReservation(BoatReservation oldBoatReservation) {
      if (oldBoatReservation == null)
         return;
      if (this.boatReservation != null)
         if (this.boatReservation.contains(oldBoatReservation))
         {
            this.boatReservation.remove(oldBoatReservation);
            oldBoatReservation.setBoat((Boat)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllBoatReservation() {
      if (boatReservation != null)
      {
         BoatReservation oldBoatReservation;
         for (java.util.Iterator iter = getIteratorBoatReservation(); iter.hasNext();)
         {
            oldBoatReservation = (BoatReservation)iter.next();
            iter.remove();
            oldBoatReservation.setBoat((Boat)null);
         }
      }
   }

}