package code.dto.entities.cottage;

import code.model.Location;
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
    private Location location;
    private int roomNumber;
    private int bedNumber;
    private int pricePerDay;
    private List<CottageReservationTag> additionalServices;
    private CottageOwner cottageOwner;
    private List<PictureBase64> pictures;
}
