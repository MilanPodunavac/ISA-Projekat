package code.dto.fishing_instructor;

import code.model.Client;
import code.model.FishingInstructorAvailablePeriod;
import code.model.FishingTrip;
import code.model.cottage.CottageReservationTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FishingInstructorGetDto {
    @NotBlank(message = "Id is required")
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean enabled;
    private String reasonForRegistration;
    private String biography;
    private Set<FishingTrip> fishingTrips;
    private Set<FishingInstructorAvailablePeriod> fishingInstructorAvailablePeriods;
    private Set<Client> subscribers;
}
