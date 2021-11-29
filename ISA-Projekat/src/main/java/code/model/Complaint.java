package code.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Complaint {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @Column
   private String description;
   @Column
   private String response;
   @Column
   private boolean responded;
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

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getResponse() {
      return response;
   }

   public void setResponse(String response) {
      this.response = response;
   }

   public boolean isResponded() {
      return responded;
   }

   public void setResponded(boolean responded) {
      this.responded = responded;
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