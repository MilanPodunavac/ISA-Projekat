package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.*;
import code.dto.entities.boat.BoatGetDto;
import code.dto.entities.boat.NewBoatActionDto;
import code.dto.entities.cottage.*;
import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.Review;
import code.model.base.Action;
import code.model.base.AvailabilityPeriod;
import code.model.base.OwnerCommentary;
import code.model.base.Reservation;
import code.model.boat.BoatAction;
import code.model.cottage.Cottage;
import code.model.cottage.CottageAction;
import code.model.cottage.CottageReservation;
import code.model.wrappers.DateRange;
import code.repository.CottageOwnerRepository;
import code.repository.CottageRepository;
import code.repository.UserRepository;
import code.service.CottageService;
import code.utils.TokenUtils;
import io.swagger.models.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

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

    @GetMapping()
    public ResponseEntity<List<Object>> get() throws EntityNotFoundException, IOException {
        List<Cottage> cottages = _cottageService.getAllCottages();
        cottages.sort(Comparator.comparing(Cottage::getId));
        List<CottageGetDto> dtos = new ArrayList<>();
        for(Cottage cottage : cottages){
            dtos.add(createCottageGetDto(cottage));
        }
        return ResponseEntity.ok(_mapper.map(cottages, new TypeToken<List<CottageGetDto>>() {}.getType()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get(@PathVariable Integer id){
        try{
            Cottage cottage = _cottageService.getCottage(id);
            CottageGetDto cottageDto = createCottageGetDto(cottage);
            return ResponseEntity.ok(cottageDto);
        }catch(Exception ex){
            if(ex instanceof EntityNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cottage not found");
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
    }

    private CottageGetDto createCottageGetDto(Cottage cottage) throws EntityNotFoundException, IOException {
        CottageGetDto cottageDto = _mapper.map(cottage, CottageGetDto.class);
        cottageDto.setDeletable(!cottage.hasFutureReservationsOrActions());
        cottageDto.setAvailabilityPeriods(new ArrayList<>());
        cottageDto.setCottageReservations(new ArrayList<>());
        cottageDto.setCottageActions(new ArrayList<>());
        for (AvailabilityPeriod period: cottage.getAvailabilityPeriods()) {
            cottageDto.getAvailabilityPeriods().add(new AvailabilityPeriodGetDto(period.getRange().getStartDate(), period.getRange().getEndDate()));
            for(Reservation res: period.getReservations()){
                CottageReservationGetDto dto = _mapper.map(res, CottageReservationGetDto.class);
                if(res.getOwnerCommentary() != null){
                    dto.setCommentary(res.getOwnerCommentary().getCommentary());
                }
                dto.setClientFullName(res.getClient().getFirstName() + " " + res.getClient().getLastName());
                cottageDto.getCottageReservations().add(dto);
            }
            for(Action act: period.getActions()){
                CottageActionGetDto dto = _mapper.map(act, CottageActionGetDto.class);
                if(act.getOwnerCommentary() != null){
                    dto.setCommentary(act.getOwnerCommentary().getCommentary());
                }
                if(act.getClient() != null){
                    dto.setClientFullName(act.getClient().getFirstName() + " " + act.getClient().getLastName());
                }
                cottageDto.getCottageActions().add(dto);
            }
        }
        cottageDto.getAvailabilityPeriods().sort(Comparator.comparing(AvailabilityPeriodGetDto::getStartDate));
        cottageDto.getCottageReservations().sort(Comparator.comparing(CottageReservationGetDto::getStartDate));
        cottageDto.getCottageActions().sort(Comparator.comparing(CottageActionGetDto::getStartDate));
        cottageDto.setPictures(_cottageService.getCottageImagesAsBase64(cottage.getId()));
        return cottageDto;
    }

    @GetMapping(value = "/{id}/reservation/{resId}")
    public ResponseEntity<Object> getReservation(@PathVariable Integer id, @PathVariable Integer resId){
        try{
            CottageReservation reservation = _cottageService.getCottageReservation(id, resId);
            CottageReservationGetDto dto = _mapper.map(reservation, CottageReservationGetDto.class);
            if(reservation.getOwnerCommentary() != null){
                dto.setCommentary(reservation.getOwnerCommentary().getCommentary());
            }
            dto.setClientFullName(reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName());
            return ResponseEntity.ok(dto);
        }catch(Exception ex){
            if(ex instanceof EntityNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
    }

    @GetMapping(value = "/{id}/action/{actId}")
    public ResponseEntity<Object> getAction(@PathVariable Integer id, @PathVariable Integer actId){
        try{
            CottageAction action = _cottageService.getCottageAction(id, actId);
            CottageActionGetDto dto = _mapper.map(action, CottageActionGetDto.class);
            if(action.getOwnerCommentary() != null){
                dto.setCommentary(action.getOwnerCommentary().getCommentary());
            }
            if(action.getClient() != null){
                dto.setClientFullName(action.getClient().getFirstName() + " " + action.getClient().getLastName());
                dto.setClientId(action.getClient().getId());
            }
            return ResponseEntity.ok(dto);
        }catch(Exception ex){
            if(ex instanceof EntityNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> addCottage(@Valid @RequestBody CottageDto dto, BindingResult result, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
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
        return ResponseEntity.ok("Availability period added successfully");
    }

    @PostMapping(value = "/reservation")
    @PreAuthorize("hasAnyRole('ROLE_COTTAGE_OWNER', 'CLIENT')")
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
            if(ex instanceof EntityNotAvailableException || ex instanceof InvalidReservationException || ex instanceof ClientCancelledThisPeriodException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Reservation added");
    }

    @PostMapping(value = "/reservation/{id}/cancel")
    @PreAuthorize("hasAnyRole('ROLE_COTTAGE_OWNER', 'CLIENT')")
    public ResponseEntity<String> cancelReservation(@PathVariable Integer id, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            _cottageService.cancelReservation(id, email);
        }catch(Exception ex){
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotAvailableException || ex instanceof InvalidReservationException || ex instanceof ClientCancelledThisPeriodException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Reservation cancelled");
    }

    @PostMapping(value="/action")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> addAction(@Valid @RequestBody NewCottageActionDto dto, BindingResult result, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            Calendar cal = Calendar.getInstance();
            cal.setTime(dto.getStartDate());
            cal.add(Calendar.DATE, dto.getNumberOfDays());
            Date endDate = cal.getTime();
            CottageAction action = _mapper.map(dto, CottageAction.class);
            action.setId(0);
            action.setRange(new DateRange(dto.getStartDate(), endDate));
            _cottageService.addAction(email, dto.getCottageId(), action);
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotAvailableException || ex instanceof InvalidReservationException || ex instanceof ClientCancelledThisPeriodException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Action added");
    }

    @PostMapping(value="/{id}/picture")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> addPicture(@PathVariable Integer id, @RequestParam(name="pictures", required=false) MultipartFile picture, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            _cottageService.addPicture(id, picture, email);
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Picture added");
    }

    @DeleteMapping(value="/{id}/picture/{pic}")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> deletePicture(@PathVariable Integer id, @PathVariable int pic, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth){
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            _cottageService.deletePicture(id, pic, email);
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Picture deleted");
    }
    @DeleteMapping(value="/{id}")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> deleteCottage(@PathVariable Integer id){
        try{
            _cottageService.unlinkReferencesAndDeleteCottage(id);
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotDeletableException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Cottage deleted");
    }

    @PutMapping(value="/{id}")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> updateCottage(@PathVariable Integer id, @Valid @RequestBody CottageDto dto, BindingResult result, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            _cottageService.updateCottage(id, _mapper.map(dto, Cottage.class), email);
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotUpdateableException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Cottage updated");
    }

    @PostMapping(value="/{id}/reservation/{resId}/commentary")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> addReservationCommentary(@PathVariable Integer id, @PathVariable int resId, @RequestBody NewOwnerCommentaryDto commentary, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth){
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            _cottageService.addReservationCommentary(id, resId, email, _mapper.map(commentary, OwnerCommentary.class));
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof ReservationOrActionNotFinishedException || ex instanceof ReservationOrActionAlreadyCommented)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Commentary added");
    }

    @PostMapping(value="/{id}/action/{actId}/commentary")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> addActionCommentary(@PathVariable Integer id, @PathVariable int actId, @RequestBody NewOwnerCommentaryDto commentary, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth){
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            _cottageService.addActionCommentary(id, actId, email, _mapper.map(commentary, OwnerCommentary.class));
        } catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof ReservationOrActionNotFinishedException || ex instanceof ReservationOrActionAlreadyCommented)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Commentary added");
    }

    @PostMapping(value = "{id}/review")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> addReview(@Valid @RequestBody NewCottageReviewDto dto, @PathVariable Integer id, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            _cottageService.addReview(dto.cottageId, dto.clientId, dto.grade, dto.description);
        }
        catch (Exception ex) {
            if(ex instanceof EntityNotOwnedException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            if(ex instanceof EntityNotAvailableException || ex instanceof InvalidReservationException || ex instanceof ClientCancelledThisPeriodException)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Action added");
    }

    @GetMapping("/{id}/visit-report")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<Object> getVisitReport(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(_cottageService.generateVisitReport(id));
        }catch(Exception ex){
            if(ex instanceof UnauthorizedAccessException || ex instanceof EntityNotOwnedException) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            if(ex instanceof EntityNotFoundException || ex instanceof UserNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops, something went wrong, try again later!");
        }
    }

}
