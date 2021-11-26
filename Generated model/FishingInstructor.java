/***********************************************************************
 * Module:  FishingInstructor.java
 * Author:  User
 * Purpose: Defines the Class FishingInstructor
 ***********************************************************************/

import java.util.*;

/** @pdOid 353f6bf4-c2f5-47eb-921a-d376e9be25af */
public class FishingInstructor extends User {
   /** @pdOid e2e6abfe-2723-4bc2-8405-0f624f86127a */
   private String biography;
   
   /** @pdRoleInfo migr=no name=FishingTrip assc=association20 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   public java.util.List<FishingTrip> fishingTrip;
   
   
   /** @pdGenerated default getter */
   public java.util.List<FishingTrip> getFishingTrip() {
      if (fishingTrip == null)
         fishingTrip = new java.util.ArrayList<FishingTrip>();
      return fishingTrip;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorFishingTrip() {
      if (fishingTrip == null)
         fishingTrip = new java.util.ArrayList<FishingTrip>();
      return fishingTrip.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newFishingTrip */
   public void setFishingTrip(java.util.List<FishingTrip> newFishingTrip) {
      removeAllFishingTrip();
      for (java.util.Iterator iter = newFishingTrip.iterator(); iter.hasNext();)
         addFishingTrip((FishingTrip)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newFishingTrip */
   public void addFishingTrip(FishingTrip newFishingTrip) {
      if (newFishingTrip == null)
         return;
      if (this.fishingTrip == null)
         this.fishingTrip = new java.util.ArrayList<FishingTrip>();
      if (!this.fishingTrip.contains(newFishingTrip))
      {
         this.fishingTrip.add(newFishingTrip);
         newFishingTrip.setFishingInstructor(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldFishingTrip */
   public void removeFishingTrip(FishingTrip oldFishingTrip) {
      if (oldFishingTrip == null)
         return;
      if (this.fishingTrip != null)
         if (this.fishingTrip.contains(oldFishingTrip))
         {
            this.fishingTrip.remove(oldFishingTrip);
            oldFishingTrip.setFishingInstructor((FishingInstructor)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllFishingTrip() {
      if (fishingTrip != null)
      {
         FishingTrip oldFishingTrip;
         for (java.util.Iterator iter = getIteratorFishingTrip(); iter.hasNext();)
         {
            oldFishingTrip = (FishingTrip)iter.next();
            iter.remove();
            oldFishingTrip.setFishingInstructor((FishingInstructor)null);
         }
      }
   }

}