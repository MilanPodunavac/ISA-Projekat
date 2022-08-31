package code.dto.fishing_instructor;

import code.model.FishingInstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FishingInstructorAvailablePeriodGetDto {
    private Integer id;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private FishingInstructor fishingInstructor;
}
