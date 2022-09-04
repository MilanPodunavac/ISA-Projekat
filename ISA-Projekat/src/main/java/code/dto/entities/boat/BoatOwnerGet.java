package code.dto.entities.boat;

import code.model.Location;
import code.model.LoyaltyProgramProvider;
import code.model.Role;
import code.model.boat.Boat;
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
public class BoatOwnerGet {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean enabled;
    private Location location;
    private Role role;
    private String reasonForRegistration;
    private Set<Boat> boat;
    private double loyaltyPoints;
    private LoyaltyProgramProvider category;
}
