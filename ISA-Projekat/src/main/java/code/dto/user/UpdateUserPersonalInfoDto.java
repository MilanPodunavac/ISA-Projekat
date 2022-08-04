package code.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPersonalInfoDto {
    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @Size(min = 6, max = 12, message = "Enter valid phone number")
    @Pattern(regexp = "\\d+", message="Invalid phone number, only numbers allowed!")
    private String phoneNumber;
    @NotBlank(message = "Street Name is required")
    private String streetName;
    @NotBlank(message = "City Name is required")
    private String cityName;
    @NotBlank(message = "Country Name is required")
    private String countryName;
    private double longitude;
    private double latitude;
}
