package code.dto.entities.cottage;

import code.model.cottage.CottageReservationTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CottageGetDto {
    @NotBlank(message = "Id is required")
    private Integer id;
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
    @Min(value = 1, message = "Room number must be greater than 0")
    private int roomNumber;
    @Min(value = 1, message = "Bed number must be greater than 0")
    private int bedNumber;
    @Min(value = 1, message = "Price per day must be greater than 0")
    private int pricePerDay;
    private List<CottageReservationTag> additionalServices;
}
