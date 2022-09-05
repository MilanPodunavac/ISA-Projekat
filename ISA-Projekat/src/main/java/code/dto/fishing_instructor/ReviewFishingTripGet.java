package code.dto.fishing_instructor;

import code.model.Client;
import code.model.FishingTrip;
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
public class ReviewFishingTripGet {
    private Integer id;
    private int grade;
    private String description;
    private boolean approved;
    private Client client;
    private FishingTrip fishingTrip;
}
