package code.dto.entities;

import code.model.cottage.CottageReservationTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCottageActionDto {
    @NotNull
    private int cottageId;
    @Min(value = 1, message = "Number of people must be greater than 0")
    private int numberOfPeople;
    @NotNull
    private Date startDate;
    @Min(value = 1, message = "Number of days must be greater than 0")
    private int numberOfDays;
    private List<CottageReservationTag> cottageReservationTag;
    @NotNull
    private Date validUntilAndIncluding;
    @Min(value = 1, message = "Price must be greater than 0")
    @Max(value =50, message = "Maximum discount is 50%")
    private int discount;
}
