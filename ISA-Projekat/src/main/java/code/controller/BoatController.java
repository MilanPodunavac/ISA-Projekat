package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.NewAvailabilityPeriodDto;
import code.dto.entities.NewOwnerCommentaryDto;
import code.dto.entities.boat.BoatDto;
import code.dto.entities.boat.NewBoatActionDto;
import code.dto.entities.boat.NewBoatReservationDto;
import code.exceptions.entities.*;
import code.dto.entities.boat.BoatGetDto;
import code.dto.entities.boat.BoatDto;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.base.AvailabilityPeriod;
import code.model.base.OwnerCommentary;
import code.model.boat.Boat;
import code.model.boat.BoatAction;
import code.model.boat.BoatReservation;
import code.model.wrappers.DateRange;
import code.repository.BoatOwnerRepository;
import code.repository.BoatRepository;
import code.repository.UserRepository;
import code.service.BoatService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/boat")
public class BoatController extends BaseController {
    private final BoatService _boatService;
    private final BoatRepository _boatRepository;
    private final BoatOwnerRepository _boatOwnerRepository;
    private final UserRepository _userRepository;

    public BoatController(ModelMapper mapper, TokenUtils tokenUtils, BoatService boatService, BoatRepository boatRepository, BoatOwnerRepository boatOwnerRepository, UserRepository userRepository) {
        super(mapper, tokenUtils);
        _boatService = boatService;
        _boatRepository = boatRepository;
        _boatOwnerRepository = boatOwnerRepository;
        _userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Object>> get(){
        return ResponseEntity.ok(_mapper.map(_boatService.getAllBoats(), new TypeToken<List<BoatGetDto>>() {}.getType()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id){
        try{
            Boat boat = _boatService.getBoat(id);
            BoatGetDto boatDto = _mapper.map(boat, BoatGetDto.class);
            return ResponseEntity.ok(boatDto);
        }catch(Exception ex){
            if(ex instanceof EntityNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Boat not found");
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> addBoat(@Valid @RequestBody BoatDto dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            _boatService.addBoat(_mapper.map(dto, Boat.class));
        }catch(Exception ex){
            if(ex instanceof UserNotFoundException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Boat owner not found");
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Boat added successfully");
    }

    @PostMapping(value = "/availabilityPeriod")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> addAvailabilityPeriod(@Valid @RequestBody NewAvailabilityPeriodDto dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            AvailabilityPeriod period = new AvailabilityPeriod();
            period.setRange(new DateRange(dto.getStartDate(), dto.getEndDate()));
            _boatService.addAvailabilityPeriod(dto.getSaleEntityId(), period);
        }catch(Exception ex){
            if(ex instanceof UnauthorizedAccessException || ex instanceof EntityNotOwnedException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof AvailabilityPeriodBadRangeException)return ResponseEntity.badRequest().body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Availability period added successfully");
    }

    @PostMapping(value = "{id}/reservation")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> addReservation(@Valid @RequestBody NewBoatReservationDto dto, @PathVariable Integer id, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            Calendar cal = Calendar.getInstance();
            cal.setTime(dto.getStartDate());
            cal.add(Calendar.DATE, dto.getNumberOfDays());
            Date endDate = cal.getTime();
            BoatReservation reservation = _mapper.map(dto, BoatReservation.class);
            reservation.setId(0);
            reservation.setDateRange(new DateRange(dto.getStartDate(), endDate));
            _boatService.addReservation(dto.getClientEmail(), id, reservation);
        }catch(Exception ex){
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotAvailableException || ex instanceof InvalidReservationException || ex instanceof ClientCancelledThisPeriodException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Reservation added");
    }

    @PutMapping(value = "{boatId}/reservation/{userId}")

    @PostMapping(value = "{id}/action")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> addAction(@Valid @RequestBody NewBoatActionDto dto, @PathVariable Integer id, BindingResult  result){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            Calendar cal = Calendar.getInstance();
            cal.setTime(dto.getStartDate());
            cal.add(Calendar.DATE, dto.getNumberOfDays());
            Date endDate = cal.getTime();
            BoatAction action = _mapper.map(dto, BoatAction.class);
            action.setId(0);
            action.setRange(new DateRange(dto.getStartDate(), endDate));
            _boatService.addAction(id, action);
        }
        catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotAvailableException || ex instanceof InvalidReservationException || ex instanceof ClientCancelledThisPeriodException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Action added");
    }

    @PostMapping(value="/{id}/picture")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> addPicture(@PathVariable Integer id, @RequestParam(name="pictures", required=false) MultipartFile picture){
        try{
            _boatService.addPicture(id, picture);
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Picture added");
    }

    @DeleteMapping(value="/{id}/picture/{pic}")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> deletePicture(@PathVariable Integer id, @PathVariable int pic){
        try{
            _boatService.deletePicture(id, pic);
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Picture deleted");
    }

    @PutMapping(value="/{id}")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> updateBoat(@PathVariable Integer id, @RequestBody BoatDto dto){
        try{
            _boatService.updateBoat(id, _mapper.map(dto, Boat.class));
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotUpdateableException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Boat updated");
    }


    @PostMapping(value="/{id}/reservation/{resId}/commentary")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> addReservationCommentary(@PathVariable Integer id, @PathVariable int resId, @RequestBody NewOwnerCommentaryDto commentary){
        try{
            _boatService.addReservationCommentary(id, resId, _mapper.map(commentary, OwnerCommentary.class));
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof ReservationOrActionNotFinishedException || ex instanceof ReservationOrActionAlreadyCommented)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Commentary added");
    }

    @PostMapping(value="/{id}/action/{resId}/commentary")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> addActionCommentary(@PathVariable Integer id, @PathVariable int resId, @RequestBody NewOwnerCommentaryDto commentary){
        try{
            _boatService.addActionCommentary(id, resId, _mapper.map(commentary, OwnerCommentary.class));
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof ReservationOrActionNotFinishedException || ex instanceof ReservationOrActionAlreadyCommented)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Commentary added");
    }

    @DeleteMapping(value="/{id}")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> deleteBoat(@PathVariable Integer id){
        try{
            _boatService.unlinkReferencesAndDeleteBoat(id);
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotDeletableException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Boat deleted");
    }
}
