package code.dto.entities;

import code.dto.client.ClientGetDto;
import code.dto.entities.boat.BoatGetDto;
import code.dto.entities.cottage.CottageGetDto;
import code.model.Client;
import code.model.ReservationDiscount;
import code.model.base.AvailabilityPeriod;
import code.model.base.OwnerCommentary;
import code.model.base.ReservationStatus;
import code.model.boat.Boat;
import code.model.boat.BoatReservationTag;
import code.model.cottage.Cottage;
import code.model.cottage.CottageReservationTag;
import code.model.wrappers.DateRange;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationGetDto {
    protected Integer id;
    protected DateRange dateRange;
    protected int numberOfPeople;
    protected double price;
    protected double reservationRefund;
    protected double systemCharge;
    protected Client client;
    protected Set<ReservationDiscount> reservationDiscount;
    protected ReservationStatus reservationStatus;
    protected OwnerCommentary ownerCommentary;
    protected AvailabilityPeriod availabilityPeriod;
    protected boolean loyaltyPointsGiven;
    private Set<CottageReservationTag> cottageReservationTag;
    private Cottage cottage;
    private Set<BoatReservationTag> boatReservationTag;
    private Boat boat;
}
