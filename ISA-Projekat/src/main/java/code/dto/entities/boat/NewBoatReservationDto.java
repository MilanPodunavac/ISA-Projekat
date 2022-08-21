package code.dto.entities.boat;

import code.model.boat.BoatReservationTag;
import code.model.cottage.CottageReservationTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewBoatReservationDto {
    @NotBlank(message = "Client email required")
    private String clientEmail;
    @Min(value = 1, message = "Number of people must be greater than 0")
    private int numberOfPeople;
    @NotNull
    private Date startDate;
    @Min(value = 1, message = "Number of days must be greater than 0")
    private int numberOfDays;
    private List<BoatReservationTag> boatReservationTag;
}
