package code.controller;

import code.controller.base.BaseController;
import code.dto.fishing_trip.EditFishingTrip;
import code.dto.fishing_trip.NewFishingTrip;
import code.exceptions.admin.ModifyAnotherUserDataException;
import code.exceptions.admin.NotChangedPasswordException;
import code.exceptions.fishing_trip.EditAnotherInstructorFishingTripException;
import code.exceptions.fishing_trip.FishingTripNotFoundException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.FishingTrip;
import code.service.*;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        }
    }
}
