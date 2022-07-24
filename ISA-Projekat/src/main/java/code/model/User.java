package code.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements UserDetails {
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
   protected String phoneNumber;
   @Column
   private boolean enabled;
   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name="location_id")
   protected Location location;
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "user_role",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
   private Set<Role> roles;

   @Override
   public boolean isEnabled() {
      return enabled;
   }

   @Override
   public String getUsername() {
      return email;
   }

   @JsonIgnore
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.roles;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }
}