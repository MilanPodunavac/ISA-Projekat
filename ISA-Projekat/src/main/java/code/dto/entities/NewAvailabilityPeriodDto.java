package code.dto.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class NewAvailabilityPeriodDto {
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private int saleEntityId;
}
