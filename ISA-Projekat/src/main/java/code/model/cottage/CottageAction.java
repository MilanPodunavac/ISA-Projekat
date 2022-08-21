package code.model.cottage;

import code.model.base.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CottageAction extends Action {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cottage_id")
    private Cottage cottage;
    @ElementCollection(targetClass=CottageReservationTag.class)
    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name="cottageAction_tags")
    @Column(name="tags")
    @Fetch(value = FetchMode.JOIN)
    private Set<CottageReservationTag> additionalServices;
    private int numberOfPeople;


}
