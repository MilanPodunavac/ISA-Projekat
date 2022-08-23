package code.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentSystemTaxPercentageDTO {
    @NotNull(message = "Current system tax percentage is required")
    @Min(value = 10, message = "Current system tax percentage must be at least 10")
    @Max(value = 100, message = "Current system tax percentage can't be higher than 100")
    private double currentSystemTaxPercentage;
}
