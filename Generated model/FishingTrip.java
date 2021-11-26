/***********************************************************************
 * Module:  FishingTrip.java
 * Author:  User
 * Purpose: Defines the Class FishingTrip
 ***********************************************************************/

import java.util.*;

/** @pdOid 603f9a20-5604-491f-b331-1624196c1e5c */
public class FishingTrip extends SaleEntity {
   /** @pdOid 73e4bf3b-5a9f-42a4-98d3-7016e558c5b3 */
   private int maxPeople;
   
   /** @pdRoleInfo migr=no name=FishingInstructor assc=association20 mult=0..1 side=A */
   public FishingInstructor fishingInstructor;
   /** @pdRoleInfo migr=no name=FishingInstructorReservation assc=association21 coll=java.util.List impl=java.util.ArrayList mult=0..* side=A */
   public java.util.List<FishingInstructorReservation> fishingInstructorReservation;
   
   
   /** @pdGenerated default parent getter */
   public FishingInstructor getFishingInstructor() {
      return fishingInstructor;
   }
   
   /** @pdGenerated default parent setter
     * @param newFishingInstructor */
   public void setFishingInstructor(FishingInstructor newFishingInstructor) {
      if (this.fishingInstructor == null || !this.fishingInstructor.equals(newFishingInstructor))
      {
         if (this.fishingInstructor != null)
         {
            FishingInstructor oldFishingInstructor = this.fishingInstructor;
            this.fishingInstructor = null;
            oldFishingInstructor.removeFishingTrip(this);
         }
         if (newFishingInstructor != null)
         {
            this.fishingInstructor = newFishingInstructor;
            this.fishingInstructor.addFishingTrip(this);
         }
      }
   }
   /** @pdGenerated default getter */
   public java.util.List<FishingInstructorReservation> getFishingInstructorReservation() {
      if (fishingInstructorReservation == null)
         fishingInstructorReservation = new java.util.ArrayList<FishingInstructorReservation>();
      return fishingInstructorReservation;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorFishingInstructorReservation() {
      if (fishingInstructorReservation == null)
         fishingInstructorReservation = new java.util.ArrayList<FishingInstructorReservation>();
      return fishingInstructorReservation.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newFishingInstructorReservation */
   public void setFishingInstructorReservation(java.util.List<FishingInstructorReservation> newFishingInstructorReservation) {
      removeAllFishingInstructorReservation();
      for (java.util.Iterator iter = newFishingInstructorReservation.iterator(); iter.hasNext();)
         addFishingInstructorReservation((FishingInstructorReservation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newFishingInstructorReservation */
   public void addFishingInstructorReservation(FishingInstructorReservation newFishingInstructorReservation) {
      if (newFishingInstructorReservation == null)
         return;
      if (this.fishingInstructorReservation == null)
         this.fishingInstructorReservation = new java.util.ArrayList<FishingInstructorReservation>();
      if (!this.fishingInstructorReservation.contains(newFishingInstructorReservation))
      {
         this.fishingInstructorReservation.add(newFishingInstructorReservation);
         newFishingInstructorReservation.setFishingTrip(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldFishingInstructorReservation */
   public void removeFishingInstructorReservation(FishingInstructorReservation oldFishingInstructorReservation) {
      if (oldFishingInstructorReservation == null)
         return;
      if (this.fishingInstructorReservation != null)
         if (this.fishingInstructorReservation.contains(oldFishingInstructorReservation))
         {
            this.fishingInstructorReservation.remove(oldFishingInstructorReservation);
            oldFishingInstructorReservation.setFishingTrip((FishingTrip)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllFishingInstructorReservation() {
      if (fishingInstructorReservation != null)
      {
         FishingInstructorReservation oldFishingInstructorReservation;
         for (java.util.Iterator iter = getIteratorFishingInstructorReservation(); iter.hasNext();)
         {
            oldFishingInstructorReservation = (FishingInstructorReservation)iter.next();
            iter.remove();
            oldFishingInstructorReservation.setFishingTrip((FishingTrip)null);
         }
      }
   }

}