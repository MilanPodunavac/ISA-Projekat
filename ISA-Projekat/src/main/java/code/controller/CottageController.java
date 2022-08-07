package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.NewAvailabilityPeriodDto;
import code.dto.entities.NewCottageDto;
import code.dto.entities.NewCottageReservationDto;
import code.exceptions.entities.AvailabilityPeriodBadRangeException;
import code.exceptions.entities.EntityNotAvailableException;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.EntityNotOwnedException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.model.wrappers.DateRange;
import code.repository.CottageOwnerRepository;
import code.repository.CottageRepository;
import code.repository.UserRepository;
import code.service.CottageService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/cottage")
public class CottageController extends BaseController {
    private final CottageOwnerRepository _cottageOwnerRepository;
    private final CottageRepository _cottageRepository;
    private final UserRepository _userRepository;
    private final CottageService _cottageService;

    public CottageController(ModelMapper mapper, CottageOwnerRepository cottageOwnerRepository, UserRepository userRepository, CottageRepository cottageRepository, TokenUtils tokenUtils, CottageService cottageService) {
        super(mapper, tokenUtils);
        _cottageOwnerRepository = cottageOwnerRepository;
        _userRepository = userRepository;
        _cottageRepository = cottageRepository;
        _cottageService = cottageService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> addCottage(@Valid @RequestBody NewCottageDto dto, BindingResult result, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            Cottage cottage = _mapper.map(dto, Cottage.class);
            _cottageService.addCottage(email, cottage);
        }catch(Exception ex){
            if(ex instanceof UserNotFoundException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cottage owner not found");
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Cottage added successfully");
    }

    @PostMapping(value = "/availabilityPeriod")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> addAvailabilityPeriod(@Valid @RequestBody NewAvailabilityPeriodDto dto, BindingResult result, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            AvailabilityPeriod period = new AvailabilityPeriod();
            period.setRange(new DateRange(dto.getStartDate(), dto.getEndDate()));
            _cottageService.addAvailabilityPeriod(dto.getSaleEntityId(), period, email);
        }catch(Exception ex){
            if(ex instanceof UnauthorizedAccessException || ex instanceof EntityNotOwnedException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof AvailabilityPeriodBadRangeException)return ResponseEntity.badRequest().body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Cottage added successfully");
    }

    @PostMapping(value = "/reservation")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> addReservation(@Valid @RequestBody NewCottageReservationDto dto, BindingResult result, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            Calendar cal = Calendar.getInstance();
            cal.setTime(dto.getStartDate());
            cal.add(Calendar.DATE, dto.getNumberOfDays());
            Date endDate = cal.getTime();
            CottageReservation reservation = _mapper.map(dto, CottageReservation.class);
            reservation.setId(0);
            reservation.setDateRange(new DateRange(dto.getStartDate(), endDate));
            _cottageService.addReservation(dto.getClientEmail(), dto.getCottageId(), reservation, email);
        }catch(Exception ex){
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotAvailableException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Reservation added");
    }
}
