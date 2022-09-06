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
public class NewCottageReviewDto {
    @NotNull
    public int clientId;
    @NotNull
    public int cottageId;
    @Min(value = 1, message = "Grade must be greater or equal to 1")
    @Max(value = 5, message = "Grade must be lesser or equal to 5")
    public int grade;
    public String description;
}
