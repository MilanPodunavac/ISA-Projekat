/***********************************************************************
 * Module:  BoatOwner.java
 * Author:  User
 * Purpose: Defines the Class BoatOwner
 ***********************************************************************/

import java.util.*;

/** @pdOid a738ed1d-4fa6-483b-98a9-02a868e0eaac */
public class BoatOwner extends User {
   /** @pdRoleInfo migr=no name=Boat assc=association7 coll=java.util.List impl=java.util.ArrayList mult=0..* side=A */
   public java.util.List<Boat> boat;
   
   
   /** @pdGenerated default getter */
   public java.util.List<Boat> getBoat() {
      if (boat == null)
         boat = new java.util.ArrayList<Boat>();
      return boat;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorBoat() {
      if (boat == null)
         boat = new java.util.ArrayList<Boat>();
      return boat.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newBoat */
   public void setBoat(java.util.List<Boat> newBoat) {
      removeAllBoat();
      for (java.util.Iterator iter = newBoat.iterator(); iter.hasNext();)
         addBoat((Boat)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newBoat */
   public void addBoat(Boat newBoat) {
      if (newBoat == null)
         return;
      if (this.boat == null)
         this.boat = new java.util.ArrayList<Boat>();
      if (!this.boat.contains(newBoat))
      {
         this.boat.add(newBoat);
         newBoat.setBoatOwner(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldBoat */
   public void removeBoat(Boat oldBoat) {
      if (oldBoat == null)
         return;
      if (this.boat != null)
         if (this.boat.contains(oldBoat))
         {
            this.boat.remove(oldBoat);
            oldBoat.setBoatOwner((BoatOwner)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllBoat() {
      if (boat != null)
      {
         Boat oldBoat;
         for (java.util.Iterator iter = getIteratorBoat(); iter.hasNext();)
         {
            oldBoat = (Boat)iter.next();
            iter.remove();
            oldBoat.setBoatOwner((BoatOwner)null);
         }
      }
   }

}