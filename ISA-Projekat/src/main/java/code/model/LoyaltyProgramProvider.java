package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoyaltyProgramProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private double pointsNeeded;
    @Column
    private double lesserSystemTaxPercentage;
    @Enumerated(EnumType.STRING)
    private LoyaltyProgramCategory category;
}
