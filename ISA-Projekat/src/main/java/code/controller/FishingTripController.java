package code.controller;

import code.controller.base.BaseController;
import code.dto.fishing_trip.EditFishingTrip;
import code.dto.fishing_trip.NewFishingTrip;
import code.dto.fishing_trip.NewQuickReservation;
import code.exceptions.fishing_trip.EditAnotherInstructorFishingTripException;
import code.exceptions.fishing_trip.FishingTripHasQuickReservationWithClientException;
import code.exceptions.fishing_trip.FishingTripNotFoundException;
import code.exceptions.fishing_trip.quick_reservation.*;
import code.model.FishingTrip;
import code.model.FishingTripQuickReservation;
import code.service.*;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/fishing-trip")
public class FishingTripController extends BaseController {
    private final FishingTripService _fishingTripService;

    public FishingTripController(FishingTripService fishingTripService, ModelMapper mapper, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        this._fishingTripService = fishingTripService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> addFishingTrip(@Valid @RequestBody NewFishingTrip dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        _fishingTripService.save(_mapper.map(dto, FishingTrip.class));
        return ResponseEntity.ok("Fishing trip added!");
    }

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> editFishingTrip(@Valid @RequestBody EditFishingTrip dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _fishingTripService.edit(_mapper.map(dto, FishingTrip.class));
            return ResponseEntity.ok("Fishing trip edited!");
        } catch (FishingTripNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (EditAnotherInstructorFishingTripException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (FishingTripHasQuickReservationWithClientException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{id}/editPictures")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> editPictures(@PathVariable Integer id, @RequestParam(name="pictures", required=false) MultipartFile[] pictures) {
        try {
            _fishingTripService.editPictures(id, pictures);
            return ResponseEntity.ok("Fishing trip pictures edited!");
        } catch (FishingTripNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (EditAnotherInstructorFishingTripException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (FishingTripHasQuickReservationWithClientException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> deleteFishingTrip(@PathVariable Integer id) {
        try {
            _fishingTripService.deleteFishingTrip(id);
            return ResponseEntity.ok("Fishing trip deleted!");
        } catch (FishingTripNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (EditAnotherInstructorFishingTripException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (FishingTripHasQuickReservationWithClientException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/{id}/addQuickReservation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> addQuickReservation(@PathVariable Integer id, @Valid @RequestBody NewQuickReservation dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _fishingTripService.addQuickReservation(id, _mapper.map(dto, FishingTripQuickReservation.class));
            return ResponseEntity.ok("Fishing trip quick reservation added!");
        } catch (QuickReservationStartDateInPastException | ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AddQuickReservationToAnotherInstructorFishingTripException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (FishingTripNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException | FishingTripReservationTagsDontContainQuickReservationTagException | NoAvailablePeriodForQuickReservationException | QuickReservationOverlappingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
