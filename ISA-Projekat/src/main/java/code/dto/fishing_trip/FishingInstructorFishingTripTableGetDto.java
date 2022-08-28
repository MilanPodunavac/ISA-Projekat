package code.dto.fishing_trip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FishingInstructorFishingTripTableGetDto {
    private Integer id;
    private String name;
    private Integer maxPeople;
    private double costPerDay;
    private String address;
    private String city;
    private String country;
}
