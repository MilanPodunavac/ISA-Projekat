package code.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
   @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq", initialValue = 2, allocationSize = 1)
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
   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
   @JoinColumn(name="role_id")
   private Role role;

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
      Set<Role> roles = new HashSet<>();
      roles.add(this.role);
      return roles;
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