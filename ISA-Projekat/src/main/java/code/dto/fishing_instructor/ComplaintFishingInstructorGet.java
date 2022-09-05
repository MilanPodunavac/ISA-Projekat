package code.dto.fishing_instructor;

import code.model.Client;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintFishingInstructorGet {
    private Integer id;
    private String description;
    private Client client;
    private FishingInstructor fishingInstructor;
}
