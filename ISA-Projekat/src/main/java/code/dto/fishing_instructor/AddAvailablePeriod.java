package code.dto.fishing_instructor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddAvailablePeriod {
    @NotNull(message = "Available from is required")
    private Date availableFrom;
    @NotNull(message = "Available to is required")
    private Date availableTo;
}
