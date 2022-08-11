package code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FishingTripQuickReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDate start;
    @Column
    private Integer durationInDays;
    @Column
    private LocalDate validUntilAndIncluding;
    @Column
    private Integer maxPeople;
    @Column
    private Integer price;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="location_id")
    private Location location;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fishingTrip_id")
    private FishingTrip fishingTrip;
    @ElementCollection(targetClass= FishingTripReservationTag.class)
    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name="fishingTripQuickReservation_tags")
    @Column(name="tags")
    private Set<FishingTripReservationTag> fishingTripReservationTags;
}
