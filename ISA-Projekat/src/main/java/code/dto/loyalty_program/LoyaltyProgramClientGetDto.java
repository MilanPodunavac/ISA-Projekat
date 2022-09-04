package code.dto.loyalty_program;

import code.model.LoyaltyProgramCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyProgramClientGetDto {
    private Integer id;
    private double pointsNeeded;
    private double discountPercentage;
    private LoyaltyProgramCategory category;
}
