/***********************************************************************
 * Module:  CottageOwner.java
 * Author:  User
 * Purpose: Defines the Class CottageOwner
 ***********************************************************************/

import java.util.*;

/** @pdOid 5d9c7d05-5fde-4ad7-bf99-affbacb9a8a7 */
public class CottageOwner extends User {
   /** @pdRoleInfo migr=no name=Cottage assc=association6 coll=java.util.List impl=java.util.ArrayList mult=0..* side=A */
   public java.util.List<Cottage> cottage;
   
   
   /** @pdGenerated default getter */
   public java.util.List<Cottage> getCottage() {
      if (cottage == null)
         cottage = new java.util.ArrayList<Cottage>();
      return cottage;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorCottage() {
      if (cottage == null)
         cottage = new java.util.ArrayList<Cottage>();
      return cottage.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newCottage */
   public void setCottage(java.util.List<Cottage> newCottage) {
      removeAllCottage();
      for (java.util.Iterator iter = newCottage.iterator(); iter.hasNext();)
         addCottage((Cottage)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newCottage */
   public void addCottage(Cottage newCottage) {
      if (newCottage == null)
         return;
      if (this.cottage == null)
         this.cottage = new java.util.ArrayList<Cottage>();
      if (!this.cottage.contains(newCottage))
      {
         this.cottage.add(newCottage);
         newCottage.setCottageOwner(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldCottage */
   public void removeCottage(Cottage oldCottage) {
      if (oldCottage == null)
         return;
      if (this.cottage != null)
         if (this.cottage.contains(oldCottage))
         {
            this.cottage.remove(oldCottage);
            oldCottage.setCottageOwner((CottageOwner)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllCottage() {
      if (cottage != null)
      {
         Cottage oldCottage;
         for (java.util.Iterator iter = getIteratorCottage(); iter.hasNext();)
         {
            oldCottage = (Cottage)iter.next();
            iter.remove();
            oldCottage.setCottageOwner((CottageOwner)null);
         }
      }
   }

}