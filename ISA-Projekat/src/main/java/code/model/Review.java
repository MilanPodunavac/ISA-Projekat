package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Review {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column
   private int grade;
   @Column
   private String description;
   @Column
   private boolean approved;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "client_id")
   private Client client;
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "saleEntity_id")
   private SaleEntity saleEntity;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public int getGrade() {
      return grade;
   }

   public void setGrade(int grade) {
      this.grade = grade;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public boolean isApproved() {
      return approved;
   }

   public void setApproved(boolean approved) {
      this.approved = approved;
   }

   public Client getClient() {
      return client;
   }

   public void setClient(Client client) {
      this.client = client;
   }

   public SaleEntity getSaleEntity() {
      return saleEntity;
   }

   public void setSaleEntity(SaleEntity saleEntity) {
      this.saleEntity = saleEntity;
   }
}