/***********************************************************************
 * Module:  CottageReservation.java
 * Author:  User
 * Purpose: Defines the Class CottageReservation
 ***********************************************************************/

import java.util.*;

/** @pdOid 6acd6324-d005-462d-90a3-3c2143d26a09 */
public class CottageReservation extends Reservation {
   /** @pdRoleInfo migr=no name=CottageReservationTag assc=association14 coll=java.util.List impl=java.util.ArrayList mult=0..* type=Composition */
   public java.util.List<CottageReservationTag> cottageReservationTag;
   /** @pdRoleInfo migr=no name=Cottage assc=association8 mult=0..1 side=A */
   public Cottage cottage;
   
   
   /** @pdGenerated default getter */
   public java.util.List<CottageReservationTag> getCottageReservationTag() {
      if (cottageReservationTag == null)
         cottageReservationTag = new java.util.ArrayList<CottageReservationTag>();
      return cottageReservationTag;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorCottageReservationTag() {
      if (cottageReservationTag == null)
         cottageReservationTag = new java.util.ArrayList<CottageReservationTag>();
      return cottageReservationTag.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newCottageReservationTag */
   public void setCottageReservationTag(java.util.List<CottageReservationTag> newCottageReservationTag) {
      removeAllCottageReservationTag();
      for (java.util.Iterator iter = newCottageReservationTag.iterator(); iter.hasNext();)
         addCottageReservationTag((CottageReservationTag)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newCottageReservationTag */
   public void addCottageReservationTag(CottageReservationTag newCottageReservationTag) {
      if (newCottageReservationTag == null)
         return;
      if (this.cottageReservationTag == null)
         this.cottageReservationTag = new java.util.ArrayList<CottageReservationTag>();
      if (!this.cottageReservationTag.contains(newCottageReservationTag))
         this.cottageReservationTag.add(newCottageReservationTag);
   }
   
   /** @pdGenerated default remove
     * @param oldCottageReservationTag */
   public void removeCottageReservationTag(CottageReservationTag oldCottageReservationTag) {
      if (oldCottageReservationTag == null)
         return;
      if (this.cottageReservationTag != null)
         if (this.cottageReservationTag.contains(oldCottageReservationTag))
            this.cottageReservationTag.remove(oldCottageReservationTag);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllCottageReservationTag() {
      if (cottageReservationTag != null)
         cottageReservationTag.clear();
   }
   /** @pdGenerated default parent getter */
   public Cottage getCottage() {
      return cottage;
   }
   
   /** @pdGenerated default parent setter
     * @param newCottage */
   public void setCottage(Cottage newCottage) {
      if (this.cottage == null || !this.cottage.equals(newCottage))
      {
         if (this.cottage != null)
         {
            Cottage oldCottage = this.cottage;
            this.cottage = null;
            oldCottage.removeCottageReservation(this);
         }
         if (newCottage != null)
         {
            this.cottage = newCottage;
            this.cottage.addCottageReservation(this);
         }
      }
   }

}