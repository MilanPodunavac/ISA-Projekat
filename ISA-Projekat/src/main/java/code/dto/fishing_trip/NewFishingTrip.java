package code.dto.fishing_trip;

import code.model.FishingTripReservationTag;
import code.validation.FishingTripReservationTagValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewFishingTrip {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Rules are required")
    private String rules;
    @NotBlank(message = "Equipment is required")
    private String equipment;
    @NotNull(message = "Max people is required")
    @Min(value = 1, message = "Max people must be at least 1")
    private Integer maxPeople;
    @NotNull(message = "Cost per day is required")
    @Min(value = 1, message = "Cost per day must be at least 1")
    private double costPerDay;
    @NotNull(message = "Percentage instructor keeps if reservation is cancelled is required")
    @Min(value = 0, message = "Percentage instructor keeps if reservation is cancelled must be at least 0")
    @Max(value = 100, message = "Percentage instructor keeps if reservation is cancelled can't be higher than 100")
    private double percentageInstructorKeepsIfReservationCancelled;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Country is required")
    private String country;
    private double longitude;
    private double latitude;
    @FishingTripReservationTagValidator(targetClassType = FishingTripReservationTag.class, message = "Fishing trip reservation tags not valid")
    private Set<String> fishingTripReservationTags;
}
