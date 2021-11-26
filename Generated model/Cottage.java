/***********************************************************************
 * Module:  Cottage.java
 * Author:  User
 * Purpose: Defines the Class Cottage
 ***********************************************************************/

import java.util.*;

/** @pdOid 0ecaf919-c11e-497c-8712-83367ac142a9 */
public class Cottage extends SaleEntity {
   /** @pdOid 27913e24-0d24-49dc-9706-70679291c556 */
   private int roomNumber;
   /** @pdOid affc5835-d762-4c34-a4c5-413a7dc483d3 */
   private int bedNumber;
   
   /** @pdRoleInfo migr=no name=CottageOwner assc=association6 mult=0..1 */
   public CottageOwner cottageOwner;
   /** @pdRoleInfo migr=no name=CottageReservation assc=association8 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   public java.util.List<CottageReservation> cottageReservation;
   
   
   /** @pdGenerated default parent getter */
   public CottageOwner getCottageOwner() {
      return cottageOwner;
   }
   
   /** @pdGenerated default parent setter
     * @param newCottageOwner */
   public void setCottageOwner(CottageOwner newCottageOwner) {
      if (this.cottageOwner == null || !this.cottageOwner.equals(newCottageOwner))
      {
         if (this.cottageOwner != null)
         {
            CottageOwner oldCottageOwner = this.cottageOwner;
            this.cottageOwner = null;
            oldCottageOwner.removeCottage(this);
         }
         if (newCottageOwner != null)
         {
            this.cottageOwner = newCottageOwner;
            this.cottageOwner.addCottage(this);
         }
      }
   }
   /** @pdGenerated default getter */
   public java.util.List<CottageReservation> getCottageReservation() {
      if (cottageReservation == null)
         cottageReservation = new java.util.ArrayList<CottageReservation>();
      return cottageReservation;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorCottageReservation() {
      if (cottageReservation == null)
         cottageReservation = new java.util.ArrayList<CottageReservation>();
      return cottageReservation.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newCottageReservation */
   public void setCottageReservation(java.util.List<CottageReservation> newCottageReservation) {
      removeAllCottageReservation();
      for (java.util.Iterator iter = newCottageReservation.iterator(); iter.hasNext();)
         addCottageReservation((CottageReservation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newCottageReservation */
   public void addCottageReservation(CottageReservation newCottageReservation) {
      if (newCottageReservation == null)
         return;
      if (this.cottageReservation == null)
         this.cottageReservation = new java.util.ArrayList<CottageReservation>();
      if (!this.cottageReservation.contains(newCottageReservation))
      {
         this.cottageReservation.add(newCottageReservation);
         newCottageReservation.setCottage(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldCottageReservation */
   public void removeCottageReservation(CottageReservation oldCottageReservation) {
      if (oldCottageReservation == null)
         return;
      if (this.cottageReservation != null)
         if (this.cottageReservation.contains(oldCottageReservation))
         {
            this.cottageReservation.remove(oldCottageReservation);
            oldCottageReservation.setCottage((Cottage)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllCottageReservation() {
      if (cottageReservation != null)
      {
         CottageReservation oldCottageReservation;
         for (java.util.Iterator iter = getIteratorCottageReservation(); iter.hasNext();)
         {
            oldCottageReservation = (CottageReservation)iter.next();
            iter.remove();
            oldCottageReservation.setCottage((Cottage)null);
         }
      }
   }

}