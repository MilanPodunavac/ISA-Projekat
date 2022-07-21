package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}