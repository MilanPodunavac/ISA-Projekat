package code.dto.entities.cottage;

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
public class CottageActionGetDto {
    private int id;
    private Date startDate;
    private Date endDate;
    private Date validUntilAndIncluding;
    private int price;
    private int discount;
    private String clientFullName;
    private int clientId;
    private boolean reserved;
    private boolean ownerCommentable;
    private int numberOfPeople;
    private List<CottageReservationTag> additionalServices;
    private String commentary;
}
