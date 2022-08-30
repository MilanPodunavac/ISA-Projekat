package code.dto.entities.cottage;

import code.model.base.ReservationStatus;
import code.model.cottage.CottageReservationTag;
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
public class CottageReservationGetDto {
    private int id;
    private Date startDate;
    private Date endDate;
    private String clientFullName;
    private int clientId;
    private ReservationStatus reservationStatus;
    private boolean ownerCommentable;
    private int numberOfPeople;
    private List<CottageReservationTag> cottageReservationTag;
    private int price;
    private String commentary;
}
