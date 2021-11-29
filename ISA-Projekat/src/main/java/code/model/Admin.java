package code.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.*;

@Entity
public class Admin extends User {
   @Column
   private boolean mainAdmin;
   @Column
   private boolean changePassword;
   private static double systemCharge;

   public boolean isMainAdmin() {
      return mainAdmin;
   }

   public void setMainAdmin(boolean mainAdmin) {
      this.mainAdmin = mainAdmin;
   }

   public boolean isChangePassword() {
      return changePassword;
   }

   public void setChangePassword(boolean changePassword) {
      this.changePassword = changePassword;
   }

   public static double getSystemCharge() {
      return systemCharge;
   }

   public static void setSystemCharge(double systemCharge) {
      Admin.systemCharge = systemCharge;
   }
}