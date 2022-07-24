package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
   private String cityName;
   @Column
   private String countryName;

}