package code.dto.provider_registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeclineRegistrationRequestDTO {
    @NotBlank(message = "Decline reason is required")
    private String declineReason;
}
