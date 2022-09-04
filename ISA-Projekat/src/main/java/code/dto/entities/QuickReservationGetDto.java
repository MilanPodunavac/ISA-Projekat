package code.dto.entities;

import code.model.Client;
import code.model.base.AvailabilityPeriod;
import code.model.base.OwnerCommentary;
import code.model.wrappers.DateRange;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuickReservationGetDto {
    private int id;
    private DateRange range;
    private Date validUntilAndIncluding;
    private int price;
    private int discount;
    private double systemCharge;
    private double actionRefund;
    private Client client;
    private AvailabilityPeriod availabilityPeriod;
    private boolean reserved;
    private OwnerCommentary ownerCommentary;
    private boolean loyaltyPointsGiven;
}
