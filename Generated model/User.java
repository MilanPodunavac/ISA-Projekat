/***********************************************************************
 * Module:  User.java
 * Author:  User
 * Purpose: Defines the Class User
 ***********************************************************************/

import java.util.*;

/** @pdOid 85bfedc2-ad37-49cd-af5d-a227b69c8c95 */
public class User {
   /** @pdOid ce697f90-a9f5-433f-a61e-52b6f46706f7 */
   protected long id;
   /** @pdOid 4766e3ab-76db-4d89-b20e-7d1af02f1096 */
   protected String firstName;
   /** @pdOid fb1e74de-d3e1-43c1-afc8-3483f09f64f9 */
   protected String lastName;
   /** @pdOid 4692aa4f-4231-430e-80e7-e25e01bca362 */
   protected String email;
   /** @pdOid 05583446-1330-462e-b62a-90ef31e40386 */
   protected String password;
   /** @pdOid 42ab06e5-48e9-48f6-b25d-6c409b94f91e */
   protected Date dateOfBirth;
   /** @pdOid 83c6a67b-c7ff-42dd-a5b2-35032727829c */
   protected String phoneNumber;
   
   /** @pdRoleInfo migr=no name=Location assc=association1 mult=0..1 type=Composition */
   public Location location;
   /** @pdRoleInfo migr=no name=Gender assc=association17 mult=0..1 type=Composition */
   public Gender gender;

}