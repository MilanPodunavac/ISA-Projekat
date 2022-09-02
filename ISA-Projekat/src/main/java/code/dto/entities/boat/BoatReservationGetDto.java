package code.dto.entities.boat;

import code.model.base.ReservationStatus;
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
public class BoatReservationGetDto {
    private int id;
    private Date startDate;
    private Date endDate;
    private String clientFirstName;
    private String clientLastName;
    private int clientId;
    private ReservationStatus reservationStatus;
    private boolean ownerCommentable;
    private int numberOfPeople;
    private List<BoatReservationTag> boatReservationTag;
    private int price;
    private String commentary;
}
