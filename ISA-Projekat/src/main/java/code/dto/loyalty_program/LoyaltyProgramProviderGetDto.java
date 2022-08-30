package code.dto.loyalty_program;

import code.model.LoyaltyProgramCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyProgramProviderGetDto {
    private Integer id;
    private double pointsNeeded;
    private double lesserSystemTaxPercentage;
    private LoyaltyProgramCategory category;
}
