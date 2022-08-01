package code.dto;

import code.dto.enums.ProviderTypeDto;
import code.utils.EnumValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String city;
    private String country;
    private String reasonForRegistration;
    private String biography;
    private String providerType;
}
