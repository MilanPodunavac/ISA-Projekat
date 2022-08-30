package code.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewAvailabilityPeriodDto {
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private int saleEntityId;
}
