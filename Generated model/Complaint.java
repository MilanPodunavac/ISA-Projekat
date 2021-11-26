/***********************************************************************
 * Module:  Complaint.java
 * Author:  User
 * Purpose: Defines the Class Complaint
 ***********************************************************************/

import java.util.*;

/** @pdOid 7e037fd2-fc18-4de5-a9c3-731c521d564f */
public class Complaint {
   /** @pdOid 4348116d-de30-4d3b-bff4-51e7e724593e */
   private long id;
   /** @pdOid fb19f26c-2f42-473c-a2cd-e39f70975fbd */
   private String description;
   /** @pdOid fb658ebd-8b98-4cde-a216-d98a524579b8 */
   private String response;
   /** @pdOid 1d7509b4-bebc-4731-bcd8-88c672266c93 */
   private boolean responded;
   
   /** @pdRoleInfo migr=no name=Client assc=association25 mult=0..1 */
   public Client client;
   /** @pdRoleInfo migr=no name=SaleEntity assc=association24 mult=0..1 side=A */
   public SaleEntity saleEntity;

}