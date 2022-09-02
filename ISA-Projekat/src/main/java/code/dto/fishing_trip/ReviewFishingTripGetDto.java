package code.dto.fishing_trip;

import code.model.Client;
import code.model.FishingTrip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFishingTripGetDto {
    private Integer id;
    private int grade;
    private String description;
    private boolean approved;
    private Client client;
    private FishingTrip fishingTrip;
}
