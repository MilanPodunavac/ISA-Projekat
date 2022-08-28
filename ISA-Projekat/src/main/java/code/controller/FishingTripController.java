package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.NewOwnerCommentaryDto;
import code.dto.fishing_trip.*;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.EntityNotOwnedException;
import code.exceptions.entities.ReservationOrActionAlreadyCommented;
import code.exceptions.entities.ReservationOrActionNotFinishedException;
import code.exceptions.fishing_trip.EditAnotherInstructorFishingTripException;
import code.exceptions.fishing_trip.FishingTripHasQuickReservationWithClientException;
import code.exceptions.fishing_trip.FishingTripHasReservationException;
import code.exceptions.fishing_trip.FishingTripNotFoundException;
import code.exceptions.fishing_trip.quick_reservation.*;
import code.exceptions.fishing_trip.reservation.*;
import code.model.FishingTrip;
import code.model.FishingTripQuickReservation;
import code.model.FishingTripReservation;
import code.model.base.OwnerCommentary;
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
import java.util.List;

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
        } catch (FishingTripHasQuickReservationWithClientException | FishingTripHasReservationException e) {
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
        } catch (FishingTripHasQuickReservationWithClientException | FishingTripHasReservationException e) {
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
        } catch (FishingTripHasQuickReservationWithClientException | FishingTripHasReservationException e) {
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
        } catch (ReservationStartDateInPastException | ValidUntilAndIncludingDateInPastOrAfterOrEqualToStartDateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AddReservationToAnotherInstructorFishingTripException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (FishingTripNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (FishingTripQuickReservationMaxPeopleHigherThanFishingTripMaxPeopleException |
                 FishingTripReservationTagsDontContainReservationTagException |
                 NoAvailablePeriodForReservationException | InstructorBusyDuringReservationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/{fishingTripId}/addReservation/{clientId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> addReservation(@PathVariable Integer fishingTripId, @PathVariable Integer clientId, @Valid @RequestBody NewReservation dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _fishingTripService.addReservation(fishingTripId, clientId, _mapper.map(dto, FishingTripReservation.class));
            return ResponseEntity.ok("Fishing trip reservation added!");
        } catch (ReservationStartDateInPastException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AddReservationToAnotherInstructorFishingTripException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (FishingTripNotFoundException | EnabledClientDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (FishingTripReservationTagsDontContainReservationTagException | NoAvailablePeriodForReservationException | InstructorBusyDuringReservationException | FishingTripReservationNumberOfPeopleHigherThanFishingTripMaxPeopleException | ClientBannedException | ClientBusyDuringReservationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/reservation/{reservationId}/commentary", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> addReservationCommentary(@PathVariable Integer reservationId, @RequestBody NewOwnerCommentaryDto dto) {
       try {
           _fishingTripService.addReservationCommentary(reservationId, _mapper.map(dto, OwnerCommentary.class));
           return ResponseEntity.ok("Reservation commentary added!");
       } catch (ReservationOrActionNotFinishedException | ReservationOrActionAlreadyCommented e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
       } catch (EntityNotFoundException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       } catch (EntityNotOwnedException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
       }
    }

    @PostMapping(value = "/quickReservation/{quickReservationId}/commentary", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> addQuickReservationCommentary(@PathVariable Integer quickReservationId, @RequestBody NewOwnerCommentaryDto dto) {
        try {
            _fishingTripService.addQuickReservationCommentary(quickReservationId, _mapper.map(dto, OwnerCommentary.class));
            return ResponseEntity.ok("Quick reservation commentary added!");
        } catch (ReservationOrActionNotFinishedException | ReservationOrActionAlreadyCommented e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (EntityNotOwnedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/fishingInstructorFishingTrips")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<FishingInstructorFishingTripTableGetDto>> getFishingInstructorFishingTrips() {
        return ResponseEntity.ok(_fishingTripService.getFishingInstructorFishingTrips());
    }
}
