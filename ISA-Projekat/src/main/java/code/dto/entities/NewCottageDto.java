package code.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCottageDto {
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
}
