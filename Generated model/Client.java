/***********************************************************************
 * Module:  Client.java
 * Author:  User
 * Purpose: Defines the Class Client
 ***********************************************************************/

import java.util.*;

/** @pdOid 768f0fa8-b346-4a2c-8c7b-a3f51c3f19b6 */
public class Client extends User {
   /** @pdOid de97c11f-bef2-4b50-898e-4053d6b3530c */
   private int penaltyPoints;
   /** @pdOid ca89bce5-c702-44ba-abb1-3bc065ce8c74 */
   private boolean banned;
   
   /** @pdRoleInfo migr=no name=Reservation assc=association11 coll=java.util.List impl=java.util.ArrayList mult=0..* side=A */
   public java.util.List<Reservation> reservation;
   /** @pdRoleInfo migr=no name=SaleEntity assc=association19 coll=java.util.List impl=java.util.ArrayList mult=0..* side=A */
   public java.util.List<SaleEntity> saleEntity;
   /** @pdRoleInfo migr=no name=Review assc=association22 coll=java.util.List impl=java.util.ArrayList mult=0..* side=A */
   public java.util.List<Review> review;
   
   
   /** @pdGenerated default getter */
   public java.util.List<Reservation> getReservation() {
      if (reservation == null)
         reservation = new java.util.ArrayList<Reservation>();
      return reservation;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorReservation() {
      if (reservation == null)
         reservation = new java.util.ArrayList<Reservation>();
      return reservation.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newReservation */
   public void setReservation(java.util.List<Reservation> newReservation) {
      removeAllReservation();
      for (java.util.Iterator iter = newReservation.iterator(); iter.hasNext();)
         addReservation((Reservation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newReservation */
   public void addReservation(Reservation newReservation) {
      if (newReservation == null)
         return;
      if (this.reservation == null)
         this.reservation = new java.util.ArrayList<Reservation>();
      if (!this.reservation.contains(newReservation))
      {
         this.reservation.add(newReservation);
         newReservation.setClient(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldReservation */
   public void removeReservation(Reservation oldReservation) {
      if (oldReservation == null)
         return;
      if (this.reservation != null)
         if (this.reservation.contains(oldReservation))
         {
            this.reservation.remove(oldReservation);
            oldReservation.setClient((Client)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllReservation() {
      if (reservation != null)
      {
         Reservation oldReservation;
         for (java.util.Iterator iter = getIteratorReservation(); iter.hasNext();)
         {
            oldReservation = (Reservation)iter.next();
            iter.remove();
            oldReservation.setClient((Client)null);
         }
      }
   }
   /** @pdGenerated default getter */
   public java.util.List<SaleEntity> getSaleEntity() {
      if (saleEntity == null)
         saleEntity = new java.util.ArrayList<SaleEntity>();
      return saleEntity;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorSaleEntity() {
      if (saleEntity == null)
         saleEntity = new java.util.ArrayList<SaleEntity>();
      return saleEntity.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newSaleEntity */
   public void setSaleEntity(java.util.List<SaleEntity> newSaleEntity) {
      removeAllSaleEntity();
      for (java.util.Iterator iter = newSaleEntity.iterator(); iter.hasNext();)
         addSaleEntity((SaleEntity)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newSaleEntity */
   public void addSaleEntity(SaleEntity newSaleEntity) {
      if (newSaleEntity == null)
         return;
      if (this.saleEntity == null)
         this.saleEntity = new java.util.ArrayList<SaleEntity>();
      if (!this.saleEntity.contains(newSaleEntity))
      {
         this.saleEntity.add(newSaleEntity);
         newSaleEntity.addClient(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldSaleEntity */
   public void removeSaleEntity(SaleEntity oldSaleEntity) {
      if (oldSaleEntity == null)
         return;
      if (this.saleEntity != null)
         if (this.saleEntity.contains(oldSaleEntity))
         {
            this.saleEntity.remove(oldSaleEntity);
            oldSaleEntity.removeClient(this);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllSaleEntity() {
      if (saleEntity != null)
      {
         SaleEntity oldSaleEntity;
         for (java.util.Iterator iter = getIteratorSaleEntity(); iter.hasNext();)
         {
            oldSaleEntity = (SaleEntity)iter.next();
            iter.remove();
            oldSaleEntity.removeClient(this);
         }
      }
   }
   /** @pdGenerated default getter */
   public java.util.List<Review> getReview() {
      if (review == null)
         review = new java.util.ArrayList<Review>();
      return review;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorReview() {
      if (review == null)
         review = new java.util.ArrayList<Review>();
      return review.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newReview */
   public void setReview(java.util.List<Review> newReview) {
      removeAllReview();
      for (java.util.Iterator iter = newReview.iterator(); iter.hasNext();)
         addReview((Review)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newReview */
   public void addReview(Review newReview) {
      if (newReview == null)
         return;
      if (this.review == null)
         this.review = new java.util.ArrayList<Review>();
      if (!this.review.contains(newReview))
      {
         this.review.add(newReview);
         newReview.setClient(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldReview */
   public void removeReview(Review oldReview) {
      if (oldReview == null)
         return;
      if (this.review != null)
         if (this.review.contains(oldReview))
         {
            this.review.remove(oldReview);
            oldReview.setClient((Client)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllReview() {
      if (review != null)
      {
         Review oldReview;
         for (java.util.Iterator iter = getIteratorReview(); iter.hasNext();)
         {
            oldReview = (Review)iter.next();
            iter.remove();
            oldReview.setClient((Client)null);
         }
      }
   }

}