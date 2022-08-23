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
public class PointsNeededForLoyaltyProgramCategory {
    @NotNull(message = "Points needed is required")
    @Min(value = 1, message = "Points needed must be at least 1")
    private double pointsNeeded;
}
