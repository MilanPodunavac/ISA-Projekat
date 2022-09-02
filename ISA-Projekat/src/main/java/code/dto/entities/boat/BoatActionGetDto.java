package code.dto.entities.boat;

import code.model.boat.BoatReservationTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoatActionGetDto {
    private int id;
    private Date startDate;
    private Date endDate;
    private Date validUntilAndIncluding;
    private int price;
    private int discount;
    private String clientFirstName;
    private String clientLastName;
    private int clientId;
    private boolean reserved;
    private boolean ownerCommentable;
    private int numberOfPeople;
    private List<BoatReservationTag> additionalServices;
    private String commentary;
}
