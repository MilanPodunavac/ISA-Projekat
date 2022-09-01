package code.dto.fishing_trip;

import code.model.FishingTripReservationTag;
import code.validation.FishingTripReservationTagValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewQuickReservation {
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date is required")
    private LocalDate start;
    @NotNull(message = "Duration in days is required")
    @Min(value = 1, message = "Duration in days must be at least 1")
    private Integer durationInDays;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Valid until and including date is required")
    private LocalDate validUntilAndIncluding;
    @NotNull(message = "Max people is required")
    @Min(value = 1, message = "Max people must be at least 1")
    private Integer maxPeople;
    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be at least 1")
    private double price;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Country is required")
    private String country;
    private double longitude;
    private double latitude;
    @FishingTripReservationTagValidator(targetClassType = FishingTripReservationTag.class, message = "Fishing trip reservation tags not valid")
    private Set<String> fishingTripReservationTags;
}
