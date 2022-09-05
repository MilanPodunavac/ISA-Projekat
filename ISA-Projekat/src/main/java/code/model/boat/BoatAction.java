package code.model.boat;

import code.model.base.Action;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BoatAction extends Action {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_id")
    @JsonBackReference
    private Boat boat;
    @ElementCollection(targetClass= BoatReservationTag.class)
    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name="boatAction_tags")
    @Column(name="tags")
    @Fetch(value = FetchMode.JOIN)
    private Set<BoatReservationTag> additionalServices;
    private int numberOfPeople;
    @Column
    private boolean  ownerNeeded;
}
