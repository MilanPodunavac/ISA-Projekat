package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SaleEntity {
   @Id
   @SequenceGenerator(name = "saleEntitySeqGen", sequenceName = "saleEntitySeq", initialValue = 1, allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "saleEntitySeqGen")
   protected Integer id;
   @Column
   protected String name;
   @Column
   protected String description;
   @Column
   protected String rules;
   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name="location_id")
   protected Location location;
   @ManyToMany(mappedBy = "saleEntity")
   protected List<Client> client;
   @OneToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   protected List<Review> review;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getRules() {
      return rules;
   }

   public void setRules(String rules) {
      this.rules = rules;
   }

   public Location getLocation() {
      return location;
   }

   public void setLocation(Location location) {
      this.location = location;
   }

   public List<Client> getClient() {
      return client;
   }

   public void setClient(List<Client> client) {
      this.client = client;
   }

   public List<Review> getReview() {
      return review;
   }

   public void setReview(List<Review> review) {
      this.review = review;
   }
}