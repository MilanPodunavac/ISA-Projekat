package code.dto.entities.boat;

import code.model.Location;
import code.model.base.PictureBase64;
import code.model.boat.BoatOwner;
import code.model.boat.BoatReservationTag;
import code.model.boat.NavigationalEquipment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoatGetDto {
    private Integer id;
    private String name;
    private String description;
    private String rules;
    private Location location;
    private int pricePerDay;
    private double length;
    private String type;
    private int engineNumber;
    private int enginePower;
    private int maxSpeed;
    private int maxPeople;
    private NavigationalEquipment navigationalEquipment;
    private String fishingEquipment;
    private Set<BoatReservationTag> additionalServices;
    private BoatOwner boatOwner;
    private List<PictureBase64> pictures;
}
