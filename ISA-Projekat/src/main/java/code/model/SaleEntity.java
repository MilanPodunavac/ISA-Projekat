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
   @ManyToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER)
   protected Set<Client> client;
   @OneToMany(mappedBy = "saleEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   protected Set<Review> review;
}