package code.dto.entities.cottage;

import code.model.Location;
import code.model.LoyaltyProgramProvider;
import code.model.Role;
import code.model.cottage.Cottage;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CottageOwnerGet {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean enabled;
    private Location location;
    private Role role;
    private String reasonForRegistration;
    private Set<Cottage> cottage;
    private double loyaltyPoints;
    private LoyaltyProgramProvider category;
}
