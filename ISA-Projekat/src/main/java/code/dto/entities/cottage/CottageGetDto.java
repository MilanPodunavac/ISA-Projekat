package code.dto.entities.cottage;

import code.model.base.PictureBase64;
import code.model.cottage.CottageOwner;
import code.model.cottage.CottageReservationTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CottageGetDto {
    private Integer id;
    private String name;
    private String description;
    private String rules;
    private double longitude;
    private double latitude;
    private String streetName;
    private String cityName;
    private String countryName;
    private int roomNumber;
    private int bedNumber;
    private int pricePerDay;
    private double reservationRefund;
    private List<CottageReservationTag> additionalServices;
    private CottageOwner cottageOwner;
    private List<PictureBase64> pictures;
}
