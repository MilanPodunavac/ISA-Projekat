package code.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentPointsClientGetsAfterReservationDTO {
    @NotNull(message = "Current points client gets after reservation is required")
    @Min(value = 1, message = "Current points client gets after reservation must be at least 1")
    private double currentPointsClientGetsAfterReservation;
}
