package code.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

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
}
