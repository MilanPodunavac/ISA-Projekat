package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Location {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column
   private double longitude;
   @Column
   private double latitude;
   @Column
   private String streetName;
   @Column
   private String streetNumber;
   @Column
   private String cityName;
   @Column
   private String countryName;

   public Integer getId() {
      return id;
   }

   public void setLongitude(Integer id) {
      this.id = id;
   }

   public double getLongitude() {
      return longitude;
   }

   public void setLongitude(double longitude) {
      this.longitude = longitude;
   }

   public double getLatitude() {
      return latitude;
   }

   public void setLatitude(double latitude) {
      this.latitude = latitude;
   }

   public String getStreetName() {
      return streetName;
   }

   public void setStreetName(String streetName) {
      this.streetName = streetName;
   }

   public String getStreetNumber() {
      return streetNumber;
   }

   public void setStreetNumber(String streetNumber) {
      this.streetNumber = streetNumber;
   }

   public String getCityName() {
      return cityName;
   }

   public void setCityName(String cityName) {
      this.cityName = cityName;
   }

   public String getCountryName() {
      return countryName;
   }

   public void setCountryName(String countryName) {
      this.countryName = countryName;
   }
}