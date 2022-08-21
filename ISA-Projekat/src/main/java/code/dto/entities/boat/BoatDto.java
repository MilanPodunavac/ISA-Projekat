package code.dto.entities.boat;

import code.model.boat.BoatReservationTag;
import code.model.boat.NavigationalEquipment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoatDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description name is required")
    private String description;
    @NotBlank(message = "Rules are required")
    private String rules;
    private double longitude;
    private double latitude;
    @NotBlank(message = "Street Name is required")
    private String streetName;
    @NotBlank(message = "City Name is required")
    private String cityName;
    @NotBlank(message = "Country Name is required")
    private String countryName;
    @Min(value = 1, message = "Price per day must be greater than 0")
    private int pricePerDay;
    @Min(value = 1, message = "Length must be greater than 0")
    private double length;
    @NotBlank
    private String type;
    @NotNull
    private int engineNumber;
    @NotNull
    private int enginePower;
    @NotNull
    private int maxSpeed;
    @Min(value = 1, message = "Maximum number of people must be greater than 0")
    private int maxPeople;
    @Max(value =50, message = "Maximum refund is 50%")
    private double reservationRefund;
    private NavigationalEquipment navigationalEquipment;
    private String fishingEquipment;
    private Set<BoatReservationTag> additionalServices;
}
