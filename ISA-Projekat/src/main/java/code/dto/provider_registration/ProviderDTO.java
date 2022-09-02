package code.dto.provider_registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double longitude;
    private double latitude;
    private String reasonForRegistration;
    private String biography;
    private String providerType;
}
