package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.NewOwnerCommentaryDto;
import code.dto.fishing_instructor.FishingInstructorGetDto;
import code.dto.fishing_trip.*;
import code.dto.provider_registration.ProviderDTO;
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
import code.model.FishingInstructor;
import code.model.FishingTrip;
import code.model.FishingTripQuickReservation;
import code.model.FishingTripReservation;
import code.model.base.OwnerCommentary;
import code.service.*;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fishing-trip")
public class FishingTripController extends BaseController {
    private final FishingTripService _fishingTripService;

    public FishingTripController(FishingTripService fishingTripService, ModelMapper mapper, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        this._fishingTripService = fishingTripService;
    }

    @GetMapping()
    public ResponseEntity<List<Object>> get(){
        return ResponseEntity.ok(_mapper.map(_fishingTripService.getAllFishingTrips(), new TypeToken<List<FishingTripGetDto>>() {}.getType()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id){
        try{
            FishingTrip fishingTrip = _fishingTripService.getFishingTrip(id);
            FishingTripGetDto fishingTripGetDto = _mapper.map(fishingTrip, FishingTripGetDto.class);
            fishingTripGetDto.setPictures(_fishingTripService.getFishingTripImagesAsBase64(fishingTrip.getId()));
            return ResponseEntity.ok(fishingTripGetDto);
        }catch(Exception ex){
            if(ex instanceof EntityNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fishing trip not found");
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
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
    public ResponseEntity<List<FishingTripGetDto>> getFishingInstructorFishingTrips() {
        return ResponseEntity.ok(_fishingTripService.getFishingInstructorFishingTrips().stream()
                .map(entity -> _mapper.map(entity, FishingTripGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/searchInstructorFishingTrips")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<FishingTripGetDto>> getSearchedFishingTrips(@RequestParam("searchText") String searchText) {
        return ResponseEntity.ok(_fishingTripService.getSearchedFishingTrips(searchText).stream()
                .map(entity -> _mapper.map(entity, FishingTripGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/fishingInstructorReservations")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<FishingReservationGetDto>> getFishingInstructorReservations() {
        return ResponseEntity.ok(_fishingTripService.getFishingInstructorReservations().stream()
                .map(entity -> _mapper.map(entity, FishingReservationGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/fishingInstructorQuickReservations")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<FishingQuickReservationGetDto>> getFishingInstructorQuickReservations() {
        return ResponseEntity.ok(_fishingTripService.getFishingInstructorQuickReservations().stream()
                .map(entity -> _mapper.map(entity, FishingQuickReservationGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}/freeQuickReservations")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<FishingQuickReservationGetDto>> getFishingTripFreeQuickReservations(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(_fishingTripService.getFishingTripFreeQuickReservations(id).stream()
                    .map(entity -> _mapper.map(entity, FishingQuickReservationGetDto.class))
                    .collect(Collectors.toList()));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
