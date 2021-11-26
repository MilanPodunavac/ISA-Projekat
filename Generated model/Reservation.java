/***********************************************************************
 * Module:  Reservation.java
 * Author:  User
 * Purpose: Defines the Class Reservation
 ***********************************************************************/

import java.util.*;

/** @pdOid 433854e7-3298-4ca6-a6f2-25b0475ee9e8 */
public class Reservation {
   /** @pdOid 33e0a819-72b1-4b8a-a005-ddb8deb1372c */
   protected long id;
   /** @pdOid 868f4df7-d826-44a1-84a9-f4930bd46d63 */
   protected Date startDate;
   /** @pdOid 428b0d0b-a091-41a6-a6cf-df849a55dc59 */
   protected Date endDate;
   /** @pdOid 1ab3e6ca-2ee2-4cfc-b949-4c32e89877aa */
   protected int maxPeople;
   /** @pdOid c00945b4-3db1-4447-ad69-0a19b58b7939 */
   protected double price;
   /** @pdOid ebee30a3-c1dc-43d9-af5f-b0d4b152c02f */
   protected double reservationRefund;
   /** @pdOid 587c3bf9-9ffd-430a-afa3-e972a8224138 */
   protected double systemCharge;
   
   /** @pdRoleInfo migr=no name=Client assc=association11 mult=0..1 */
   public Client client;
   /** @pdRoleInfo migr=no name=ReservationDiscount assc=association12 coll=java.util.Collection impl=java.util.HashSet mult=0..* type=Composition */
   public java.util.Collection<ReservationDiscount> reservationDiscount;
   /** @pdRoleInfo migr=no name=ReservationStatus assc=association13 mult=0..1 type=Composition */
   public ReservationStatus reservationStatus;
   
   
   /** @pdGenerated default parent getter */
   public Client getClient() {
      return client;
   }
   
   /** @pdGenerated default parent setter
     * @param newClient */
   public void setClient(Client newClient) {
      if (this.client == null || !this.client.equals(newClient))
      {
         if (this.client != null)
         {
            Client oldClient = this.client;
            this.client = null;
            oldClient.removeReservation(this);
         }
         if (newClient != null)
         {
            this.client = newClient;
            this.client.addReservation(this);
         }
      }
   }
   /** @pdGenerated default getter */
   public java.util.Collection<ReservationDiscount> getReservationDiscount() {
      if (reservationDiscount == null)
         reservationDiscount = new java.util.HashSet<ReservationDiscount>();
      return reservationDiscount;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorReservationDiscount() {
      if (reservationDiscount == null)
         reservationDiscount = new java.util.HashSet<ReservationDiscount>();
      return reservationDiscount.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newReservationDiscount */
   public void setReservationDiscount(java.util.Collection<ReservationDiscount> newReservationDiscount) {
      removeAllReservationDiscount();
      for (java.util.Iterator iter = newReservationDiscount.iterator(); iter.hasNext();)
         addReservationDiscount((ReservationDiscount)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newReservationDiscount */
   public void addReservationDiscount(ReservationDiscount newReservationDiscount) {
      if (newReservationDiscount == null)
         return;
      if (this.reservationDiscount == null)
         this.reservationDiscount = new java.util.HashSet<ReservationDiscount>();
      if (!this.reservationDiscount.contains(newReservationDiscount))
         this.reservationDiscount.add(newReservationDiscount);
   }
   
   /** @pdGenerated default remove
     * @param oldReservationDiscount */
   public void removeReservationDiscount(ReservationDiscount oldReservationDiscount) {
      if (oldReservationDiscount == null)
         return;
      if (this.reservationDiscount != null)
         if (this.reservationDiscount.contains(oldReservationDiscount))
            this.reservationDiscount.remove(oldReservationDiscount);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllReservationDiscount() {
      if (reservationDiscount != null)
         reservationDiscount.clear();
   }

}