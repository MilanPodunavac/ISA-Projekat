package code.dto.entities.cottage;

import code.model.cottage.CottageReservationTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCottageReservationDto {
    @NotBlank(message = "Client email required")
    public String clientEmail;
    @NotNull
    public int cottageId;
    @Min(value = 1, message = "Number of people must be greater than 0")
    public int numberOfPeople;
    @NotNull
    public Date startDate;
    @Min(value = 1, message = "Number of days must be greater than 0")
    public int numberOfDays;
    public List<CottageReservationTag> cottageReservationTag;
}
