/***********************************************************************
 * Module:  SaleEntity.java
 * Author:  User
 * Purpose: Defines the Class SaleEntity
 ***********************************************************************/

import java.util.*;

/** @pdOid 9176a07b-b08e-46cf-86cf-31137f30df75 */
public class SaleEntity {
   /** @pdOid 5a4f1896-d48d-462e-9872-d7e2a0457baa */
   protected long id;
   /** @pdOid db428d8c-dac9-42ea-a862-68e3502bc152 */
   protected String name;
   /** @pdOid 985cb40b-1e1f-4f93-bd83-827bb4c1f713 */
   protected String description;
   /** @pdOid 09723fc6-9c57-4688-a7af-046f938574d6 */
   protected String rules;
   
   /** @pdRoleInfo migr=no name=Location assc=association18 mult=0..1 type=Composition */
   public Location location;
   /** @pdRoleInfo migr=no name=Client assc=association19 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   public java.util.List<Client> client;
   /** @pdRoleInfo migr=no name=Review assc=association23 coll=java.util.List impl=java.util.ArrayList mult=0..* side=A */
   public java.util.List<Review> review;
   
   
   /** @pdGenerated default getter */
   public java.util.List<Client> getClient() {
      if (client == null)
         client = new java.util.ArrayList<Client>();
      return client;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorClient() {
      if (client == null)
         client = new java.util.ArrayList<Client>();
      return client.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newClient */
   public void setClient(java.util.List<Client> newClient) {
      removeAllClient();
      for (java.util.Iterator iter = newClient.iterator(); iter.hasNext();)
         addClient((Client)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newClient */
   public void addClient(Client newClient) {
      if (newClient == null)
         return;
      if (this.client == null)
         this.client = new java.util.ArrayList<Client>();
      if (!this.client.contains(newClient))
      {
         this.client.add(newClient);
         newClient.addSaleEntity(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldClient */
   public void removeClient(Client oldClient) {
      if (oldClient == null)
         return;
      if (this.client != null)
         if (this.client.contains(oldClient))
         {
            this.client.remove(oldClient);
            oldClient.removeSaleEntity(this);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllClient() {
      if (client != null)
      {
         Client oldClient;
         for (java.util.Iterator iter = getIteratorClient(); iter.hasNext();)
         {
            oldClient = (Client)iter.next();
            iter.remove();
            oldClient.removeSaleEntity(this);
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
         newReview.setSaleEntity(this);      
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
            oldReview.setSaleEntity((SaleEntity)null);
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
            oldReview.setSaleEntity((SaleEntity)null);
         }
      }
   }

}