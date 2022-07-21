package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}