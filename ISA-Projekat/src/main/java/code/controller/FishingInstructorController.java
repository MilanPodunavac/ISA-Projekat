package code.controller;

import code.controller.base.BaseController;
import code.dto.admin.PasswordDTO;
import code.dto.admin.PersonalData;
import code.dto.entities.NewComplaintDto;
import code.dto.entities.NewReviewDto;
import code.dto.fishing_instructor.*;
import code.dto.fishing_trip.FishingQuickReservationGetDto;
import code.dto.loyalty_program.LoyaltyProgramProviderGetDto;
import code.exceptions.entities.*;
import code.exceptions.fishing_instructor.AddAvailablePeriodInPastException;
import code.exceptions.fishing_instructor.AvailablePeriodOverlappingException;
import code.exceptions.fishing_instructor.AvailablePeriodStartAfterEndDateException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.FishingInstructor;
import code.model.FishingInstructorAvailablePeriod;
import code.service.FishingInstructorService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fishing-instructor")
public class FishingInstructorController extends BaseController {
    private final FishingInstructorService _fishingInstructorService;

    public FishingInstructorController(FishingInstructorService fishingInstructorService, ModelMapper mapper, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        this._fishingInstructorService = fishingInstructorService;
    }
    @GetMapping()
    public ResponseEntity<List<Object>> get(){
        return ResponseEntity.ok(_mapper.map(_fishingInstructorService.getAllFishingInstructors(), new TypeToken<List<FishingInstructorGetDto>>() {}.getType()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id){
        try{
            FishingInstructor fishingInstructor = _fishingInstructorService.getFishingInstructor(id);
            FishingInstructorGetDto fishingInstructorGetDto = _mapper.map(fishingInstructor, FishingInstructorGetDto.class);
            return ResponseEntity.ok(fishingInstructorGetDto);
        }catch(Exception ex){
            if(ex instanceof EntityNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fishing instructor not found");
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
    }

    @PostMapping(value = "/addAvailablePeriod", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> addAvailablePeriod(@Valid @RequestBody NewAvailablePeriod dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _fishingInstructorService.addAvailablePeriod(_mapper.map(dto, FishingInstructorAvailablePeriod.class));
            return ResponseEntity.ok("Available period added!");
        } catch (AvailablePeriodStartAfterEndDateException | AddAvailablePeriodInPastException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AvailablePeriodOverlappingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/changePersonalData", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> changePersonalData(@Valid @RequestBody PersonalData dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        _fishingInstructorService.changePersonalData(_mapper.map(dto, FishingInstructor.class));
        return ResponseEntity.ok("Personal data changed!");
    }

    @PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordDTO dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        _fishingInstructorService.changePassword(_mapper.map(dto, FishingInstructor.class));
        return ResponseEntity.ok("Password changed!");
    }

    @GetMapping(value = "/getLoggedInInstructor")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<FishingInstructorGetDto> getLoggedInInstructor() {
        return ResponseEntity.ok(_mapper.map(_fishingInstructorService.getLoggedInInstructor(), FishingInstructorGetDto.class));
    }

    @GetMapping(value = "/fishingInstructorAvailablePeriods")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<FishingInstructorAvailablePeriodGetDto>> getFishingInstructorAvailablePeriods() {
        return ResponseEntity.ok(_fishingInstructorService.getFishingInstructorAvailablePeriods().stream()
                .map(entity -> _mapper.map(entity, FishingInstructorAvailablePeriodGetDto.class))
                .collect(Collectors.toList()));
    }

    @PostMapping(value="/incomeInTimeInterval")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> getIncomeInTimeInterval(@Valid @RequestBody ProfitInInterval profitInInterval, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            return ResponseEntity.ok(_fishingInstructorService.getIncomeInTimeInterval(profitInInterval));
        } catch (EntityBadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/weeklyReservations")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<PeriodicalReservations>> weeklyReservations() {
        return ResponseEntity.ok(_fishingInstructorService.weeklyReservations());
    }

    @GetMapping(value = "/monthlyReservations")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<PeriodicalReservations>> monthlyReservations() {
        return ResponseEntity.ok(_fishingInstructorService.monthlyReservations());
    }

    @GetMapping(value = "/yearlyReservations")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<List<PeriodicalReservations>> yearlyReservations() {
        return ResponseEntity.ok(_fishingInstructorService.yearlyReservations());
    }

    @PostMapping(value = "{id}/review")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> addReview(@Valid @RequestBody NewReviewDto dto, @PathVariable Integer id, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            _fishingInstructorService.addReview(dto.saleEntityId, dto.clientId, dto.grade, dto.description);
        }
        catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotAvailableException || ex instanceof InvalidReservationException || ex instanceof ClientCancelledThisPeriodException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Action added");
    }

    @PostMapping(value = "{id}/complaint")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> addComplaint(@Valid @RequestBody NewComplaintDto dto, @PathVariable Integer id, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            _fishingInstructorService.addComplaint(dto.saleEntityId, dto.clientId, dto.description);
        }
        catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotAvailableException || ex instanceof InvalidReservationException || ex instanceof ClientCancelledThisPeriodException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Action added");
    }
}
