package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private Date availableFrom;
    @Column
    private Date availableTo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishingInstructor_id")
    private FishingInstructor fishingInstructor;
}
