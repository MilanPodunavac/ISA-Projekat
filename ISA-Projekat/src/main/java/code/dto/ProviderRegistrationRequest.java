package code.dto;

import code.dto.enums.ProviderTypeDto;
import code.utils.EnumValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderRegistrationRequest {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "Password too short")
    private String password;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message ="Phone number is required")
    @Size(min = 6, max = 12, message = "Enter valid phone number")
    private String phoneNumber;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "Reason for registration is required")
    private String reasonForRegistration;
    @NotBlank(message = "Biography is required")
    private String biography;
    @EnumValidator(targetClassType = ProviderTypeDto.class, message = "Type of provider is required")
    private String providerType;
}
