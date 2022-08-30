package code.dto.fishing_trip;

import code.model.*;
import code.model.base.PictureBase64;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FishingTripGetDto {
    private Integer id;
    private String name;
    private String description;
    private String rules;
    private String equipment;
    private Integer maxPeople;
    private double costPerDay;
    private double percentageInstructorKeepsIfReservationCancelled;
    private Location location;
    private FishingInstructor fishingInstructor;
    private List<PictureBase64> pictures;
    private Set<FishingTripQuickReservation> fishingTripQuickReservations;
    private Set<FishingTripReservationTag> fishingTripReservationTags;
}
