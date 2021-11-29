package code.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
   @Id
   @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
   protected Integer id;
   @Column
   protected String firstName;
   @Column
   protected String lastName;
   @Column
   protected String email;
   @Column
   protected String password;
   @Column
   protected Date dateOfBirth;
   @Column
   protected String phoneNumber;
   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name="location_id")
   protected Location location;
   @Enumerated(EnumType.ORDINAL)
   protected Gender gender;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Date getDateOfBirth() {
      return dateOfBirth;
   }

   public void setDateOfBirth(Date dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public Location getLocation() {
      return location;
   }

   public void setLocation(Location location) {
      this.location = location;
   }

   public Gender getGender() {
      return gender;
   }

   public void setGender(Gender gender) {
      this.gender = gender;
   }
}