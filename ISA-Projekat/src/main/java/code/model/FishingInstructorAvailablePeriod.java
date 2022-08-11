package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FishingInstructorAvailablePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDate availableFrom;
    @Column
    private LocalDate availableTo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishingInstructor_id")
    private FishingInstructor fishingInstructor;
}
