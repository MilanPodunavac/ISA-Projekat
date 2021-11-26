/***********************************************************************
 * Module:  FishingInstructorReservation.java
 * Author:  User
 * Purpose: Defines the Class FishingInstructorReservation
 ***********************************************************************/

import java.util.*;

/** @pdOid 542afd9e-5351-4f8f-849d-301cc90d62f6 */
public class FishingInstructorReservation extends Reservation {
   /** @pdRoleInfo migr=no name=FishingInstructorReservationTag assc=association16 coll=java.util.List impl=java.util.ArrayList mult=0..* type=Composition */
   public java.util.List<FishingInstructorReservationTag> fishingInstructorReservationTag;
   /** @pdRoleInfo migr=no name=FishingTrip assc=association21 mult=0..1 */
   public FishingTrip fishingTrip;
   
   
   /** @pdGenerated default getter */
   public java.util.List<FishingInstructorReservationTag> getFishingInstructorReservationTag() {
      if (fishingInstructorReservationTag == null)
         fishingInstructorReservationTag = new java.util.ArrayList<FishingInstructorReservationTag>();
      return fishingInstructorReservationTag;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorFishingInstructorReservationTag() {
      if (fishingInstructorReservationTag == null)
         fishingInstructorReservationTag = new java.util.ArrayList<FishingInstructorReservationTag>();
      return fishingInstructorReservationTag.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newFishingInstructorReservationTag */
   public void setFishingInstructorReservationTag(java.util.List<FishingInstructorReservationTag> newFishingInstructorReservationTag) {
      removeAllFishingInstructorReservationTag();
      for (java.util.Iterator iter = newFishingInstructorReservationTag.iterator(); iter.hasNext();)
         addFishingInstructorReservationTag((FishingInstructorReservationTag)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newFishingInstructorReservationTag */
   public void addFishingInstructorReservationTag(FishingInstructorReservationTag newFishingInstructorReservationTag) {
      if (newFishingInstructorReservationTag == null)
         return;
      if (this.fishingInstructorReservationTag == null)
         this.fishingInstructorReservationTag = new java.util.ArrayList<FishingInstructorReservationTag>();
      if (!this.fishingInstructorReservationTag.contains(newFishingInstructorReservationTag))
         this.fishingInstructorReservationTag.add(newFishingInstructorReservationTag);
   }
   
   /** @pdGenerated default remove
     * @param oldFishingInstructorReservationTag */
   public void removeFishingInstructorReservationTag(FishingInstructorReservationTag oldFishingInstructorReservationTag) {
      if (oldFishingInstructorReservationTag == null)
         return;
      if (this.fishingInstructorReservationTag != null)
         if (this.fishingInstructorReservationTag.contains(oldFishingInstructorReservationTag))
            this.fishingInstructorReservationTag.remove(oldFishingInstructorReservationTag);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllFishingInstructorReservationTag() {
      if (fishingInstructorReservationTag != null)
         fishingInstructorReservationTag.clear();
   }
   /** @pdGenerated default parent getter */
   public FishingTrip getFishingTrip() {
      return fishingTrip;
   }
   
   /** @pdGenerated default parent setter
     * @param newFishingTrip */
   public void setFishingTrip(FishingTrip newFishingTrip) {
      if (this.fishingTrip == null || !this.fishingTrip.equals(newFishingTrip))
      {
         if (this.fishingTrip != null)
         {
            FishingTrip oldFishingTrip = this.fishingTrip;
            this.fishingTrip = null;
            oldFishingTrip.removeFishingInstructorReservation(this);
         }
         if (newFishingTrip != null)
         {
            this.fishingTrip = newFishingTrip;
            this.fishingTrip.addFishingInstructorReservation(this);
         }
      }
   }

}