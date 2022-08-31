package code.dto.client;

import code.model.*;
import code.model.base.Action;
import code.model.base.Reservation;
import code.model.base.SaleEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class ClientGetDto {
    protected Integer id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    private boolean enabled;
    protected Location location;
    private Role role;
    private int penaltyPoints;
    private boolean banned;
    private Set<Reservation> reservation;
    private Set<FishingTripQuickReservation> fishingTripQuickReservations;
    private Set<FishingTripReservation> fishingTripReservations;
    private Set<Action> actions;
    private Set<SaleEntity> saleEntity;
    private Set<Review> review;
    private Set<FishingInstructor> instructorsSubscribedTo;
    private double loyaltyPoints;
    private LoyaltyProgramClient category;
}
