package code.dto.entities;

import code.model.Client;
import code.model.ReservationDiscount;
import code.model.base.AvailabilityPeriod;
import code.model.base.OwnerCommentary;
import code.model.base.ReservationStatus;
import code.model.wrappers.DateRange;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationGetDto {
    private Integer id;
    private DateRange dateRange;
    private int numberOfPeople;
    private double price;
    private double reservationRefund;
    private double systemCharge;
    private Client client;
    private Set<ReservationDiscount> reservationDiscount;
    private ReservationStatus reservationStatus;
    private OwnerCommentary ownerCommentary;
    private AvailabilityPeriod availabilityPeriod;
    private boolean loyaltyPointsGiven;
}
