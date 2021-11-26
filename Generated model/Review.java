/***********************************************************************
 * Module:  Review.java
 * Author:  User
 * Purpose: Defines the Class Review
 ***********************************************************************/

import java.util.*;

/** @pdOid c98fa007-2648-4434-bfd1-ad83749ce1df */
public class Review {
   /** @pdOid a6702dfa-0e1f-4244-8979-f95693d551dc */
   private long id;
   /** @pdOid eda19ec6-25fc-4020-b6f6-909ba95bb872 */
   private int grade;
   /** @pdOid 26005d05-0366-43f5-a2f8-ebf3c410a136 */
   private String description;
   /** @pdOid f814cfe9-2014-43a4-9719-94540f231c5d */
   private boolean approved;
   
   /** @pdRoleInfo migr=no name=Client assc=association22 mult=0..1 */
   public Client client;
   /** @pdRoleInfo migr=no name=SaleEntity assc=association23 mult=0..1 */
   public SaleEntity saleEntity;
   
   
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
            oldClient.removeReview(this);
         }
         if (newClient != null)
         {
            this.client = newClient;
            this.client.addReview(this);
         }
      }
   }
   /** @pdGenerated default parent getter */
   public SaleEntity getSaleEntity() {
      return saleEntity;
   }
   
   /** @pdGenerated default parent setter
     * @param newSaleEntity */
   public void setSaleEntity(SaleEntity newSaleEntity) {
      if (this.saleEntity == null || !this.saleEntity.equals(newSaleEntity))
      {
         if (this.saleEntity != null)
         {
            SaleEntity oldSaleEntity = this.saleEntity;
            this.saleEntity = null;
            oldSaleEntity.removeReview(this);
         }
         if (newSaleEntity != null)
         {
            this.saleEntity = newSaleEntity;
            this.saleEntity.addReview(this);
         }
      }
   }

}