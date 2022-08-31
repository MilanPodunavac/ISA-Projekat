package code.dto.fishing_trip;

import code.model.Client;
import code.model.FishingTrip;
import code.model.FishingTripReservationTag;
import code.model.Location;
import code.model.base.OwnerCommentary;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FishingQuickReservationGetDto {
    private Integer id;
    private LocalDate start;
    private Integer durationInDays;
    private LocalDate validUntilAndIncluding;
    private Integer maxPeople;
    private double price;
    private double systemTaxPercentage;
    private boolean loyaltyPointsGiven;
    private Location location;
    private FishingTrip fishingTrip;
    private Set<FishingTripReservationTag> fishingTripReservationTags;
    private Client client;
    private OwnerCommentary ownerCommentary;
}
