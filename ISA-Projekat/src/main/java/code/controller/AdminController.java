package code.controller;

import code.controller.base.BaseController;
import code.dto.admin.*;
import code.dto.client.ClientGetDto;
import code.dto.entities.ComplaintGet;
import code.dto.entities.QuickReservationGetDto;
import code.dto.entities.ReservationGetDto;
import code.dto.entities.ReviewGet;
import code.dto.entities.boat.BoatGetDto;
import code.dto.entities.boat.BoatOwnerGet;
import code.dto.entities.cottage.CottageGetDto;
import code.dto.entities.cottage.CottageOwnerGet;
import code.dto.fishing_instructor.*;
import code.dto.fishing_trip.FishingQuickReservationGetDto;
import code.dto.fishing_trip.FishingReservationGetDto;
import code.dto.loyalty_program.LoyaltyProgramClientGetDto;
import code.dto.loyalty_program.LoyaltyProgramProviderGetDto;
import code.exceptions.admin.*;
import code.exceptions.entities.*;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.Admin;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController extends BaseController {
    private final AdminService _adminService;

    public AdminController(AdminService adminService, ModelMapper mapper, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        this._adminService = adminService;
    }

    @GetMapping(value = "/loggedInAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminGetDto> getLoggedInAdmin() {
        Admin loggedInAdmin = _adminService.getLoggedInAdmin();
        return ResponseEntity.ok(_mapper.map(loggedInAdmin, AdminGetDto.class));
    }

    @PutMapping(value = "/changePersonalData", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changePersonalData(@Valid @RequestBody PersonalData dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.changePersonalData(_mapper.map(dto, Admin.class));
            return ResponseEntity.ok("Personal data changed!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> register(@Valid @RequestBody AdminRegistration dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.save(_mapper.map(dto, Admin.class));
            return ResponseEntity.ok("New admin registered!");
        } catch (NonMainAdminRegisterOtherAdminException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (EmailTakenException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordDTO dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.changePassword(_mapper.map(dto, Admin.class));
            return ResponseEntity.ok("Password changed!");
        } catch (ChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/deleteFishingInstructor/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteFishingInstructor(@PathVariable Integer id) {
        try {
            _adminService.deleteFishingInstructor(id);
            return ResponseEntity.ok("Fishing instructor deleted!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnexpectedUserRoleException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (EntityNotDeletableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/deleteCottageOwner/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCottageOwner(@PathVariable Integer id) {
        try {
            _adminService.deleteCottageOwner(id);
            return ResponseEntity.ok("Cottage owner deleted!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnexpectedUserRoleException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (EntityNotDeletableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/deleteBoatOwner/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBoatOwner(@PathVariable Integer id) {
        try {
            _adminService.deleteBoatOwner(id);
            return ResponseEntity.ok("Boat owner deleted!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnexpectedUserRoleException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (EntityNotDeletableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/deleteClient/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteClient(@PathVariable Integer id) {
        try {
            _adminService.deleteClient(id);
            return ResponseEntity.ok("Client deleted!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnexpectedUserRoleException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (EntityNotDeletableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/deleteCottage/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCottage(@PathVariable Integer id) {
        try {
            _adminService.deleteCottage(id);
            return ResponseEntity.ok("Cottage deleted!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnexpectedUserRoleException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (EntityNotDeletableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotOwnedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/deleteBoat/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBoat(@PathVariable Integer id) {
        try {
            _adminService.deleteBoat(id);
            return ResponseEntity.ok("Boat deleted!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnexpectedUserRoleException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (EntityNotDeletableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotOwnedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    @DeleteMapping(value ="cottageOwnereDeleteTest/{id}")
    public ResponseEntity<String> cottageOwnereDeleteTest(@PathVariable Integer id){
        try {
            _adminService.deleteCottageOwner(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("deleted");
    }

    @DeleteMapping(value = "/fishingReservation/{reservationId}/commentaryAccept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> fishingReservationCommentaryAccept(@PathVariable Integer reservationId) {
        try {
            _adminService.fishingReservationCommentaryAccept(reservationId);
            return ResponseEntity.ok("Fishing reservation commentary accepted!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/fishingReservation/{reservationId}/commentaryDecline")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> fishingReservationCommentaryDecline(@PathVariable Integer reservationId) {
        try {
            _adminService.fishingReservationCommentaryDecline(reservationId);
            return ResponseEntity.ok("Fishing reservation commentary declined!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/fishingQuickReservation/{quickReservationId}/commentaryAccept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> fishingQuickReservationCommentaryAccept(@PathVariable Integer quickReservationId) {
        try {
            _adminService.fishingQuickReservationCommentaryAccept(quickReservationId);
            return ResponseEntity.ok("Fishing quick reservation commentary accepted!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/fishingQuickReservation/{quickReservationId}/commentaryDecline")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> fishingQuickReservationCommentaryDecline(@PathVariable Integer quickReservationId) {
        try {
            _adminService.fishingQuickReservationCommentaryDecline(quickReservationId);
            return ResponseEntity.ok("Fishing quick reservation commentary declined!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/reservation/{reservationId}/commentaryAccept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> reservationCommentaryAccept(@PathVariable Integer reservationId) {
        try {
            _adminService.reservationCommentaryAccept(reservationId);
            return ResponseEntity.ok("Reservation commentary accepted!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/reservation/{reservationId}/commentaryDecline")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> reservationCommentaryDecline(@PathVariable Integer reservationId) {
        try {
            _adminService.reservationCommentaryDecline(reservationId);
            return ResponseEntity.ok("Reservation commentary declined!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/quickReservation/{quickReservationId}/commentaryAccept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> quickReservationCommentaryAccept(@PathVariable Integer quickReservationId) {
        try {
            _adminService.quickReservationCommentaryAccept(quickReservationId);
            return ResponseEntity.ok("Quick reservation commentary accepted!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/quickReservation/{quickReservationId}/commentaryDecline")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> quickReservationCommentaryDecline(@PathVariable Integer quickReservationId) {
        try {
            _adminService.quickReservationCommentaryDecline(quickReservationId);
            return ResponseEntity.ok("Quick reservation commentary declined!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/currentSystemTaxPercentage", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeCurrentSystemTaxPercentage(@Valid @RequestBody CurrentSystemTaxPercentageDTO dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.changeCurrentSystemTaxPercentage(_mapper.map(dto, code.model.CurrentSystemTaxPercentage.class));
            return ResponseEntity.ok("Current system tax percentage changed!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/currentPointsClientGetsAfterReservation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> currentPointsClientGetsAfterReservation(@Valid @RequestBody CurrentPointsClientGetsAfterReservationDTO dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.currentPointsClientGetsAfterReservation(_mapper.map(dto, code.model.CurrentPointsClientGetsAfterReservation.class));
            return ResponseEntity.ok("Current points client gets after reservation changed!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/currentPointsProviderGetsAfterReservation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> currentPointsProviderGetsAfterReservation(@Valid @RequestBody CurrentPointsProviderGetsAfterReservationDTO dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.currentPointsProviderGetsAfterReservation(_mapper.map(dto, code.model.CurrentPointsProviderGetsAfterReservation.class));
            return ResponseEntity.ok("Current points provider gets after reservation changed!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/changeClientPointsNeededForLoyaltyProgramCategory/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeClientPointsNeededForLoyaltyProgramCategory(@PathVariable Integer id, @Valid @RequestBody PointsNeededForLoyaltyProgramCategory dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.changeClientPointsNeededForLoyaltyProgramCategory(id, _mapper.map(dto, code.model.LoyaltyProgramClient.class));
            return ResponseEntity.ok("Client points needed for loyalty program category changed!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotUpdateableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/changeProviderPointsNeededForLoyaltyProgramCategory/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeProviderPointsNeededForLoyaltyProgramCategory(@PathVariable Integer id, @Valid @RequestBody PointsNeededForLoyaltyProgramCategory dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.changeProviderPointsNeededForLoyaltyProgramCategory(id, _mapper.map(dto, code.model.LoyaltyProgramProvider.class));
            return ResponseEntity.ok("Provider points needed for loyalty program category changed!");
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotUpdateableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/respondToComplaint/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> respondToComplaint(@PathVariable Integer id, @Valid @RequestBody ComplaintResponse complaintResponse, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.respondToComplaint(id, complaintResponse);
            return ResponseEntity.ok("Responded to the complaint!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/respondToComplaintFishingInstructor/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> respondToComplaintFishingInstructor(@PathVariable Integer id, @Valid @RequestBody ComplaintResponse complaintResponse, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.respondToComplaintFishingInstructor(id, complaintResponse);
            return ResponseEntity.ok("Responded to the complaint!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/acceptReview/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> acceptReview(@PathVariable Integer id) {
        try {
            _adminService.acceptReview(id);
            return ResponseEntity.ok("Review accepted!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (EntityNotUpdateableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/acceptReviewFishingTrip/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> acceptReviewFishingTrip(@PathVariable Integer id) {
        try {
            _adminService.acceptReviewFishingTrip(id);
            return ResponseEntity.ok("Review accepted!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (EntityNotUpdateableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/declineReview/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> declineReview(@PathVariable Integer id) {
        try {
            _adminService.declineReview(id);
            return ResponseEntity.ok("Review declined!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/declineReviewFishingTrip/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> declineReviewFishingTrip(@PathVariable Integer id) {
        try {
            _adminService.declineReviewFishingTrip(id);
            return ResponseEntity.ok("Review declined!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="/incomeInTimeInterval")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getIncomeInTimeInterval(@Valid @RequestBody ProfitInInterval profitInInterval, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            return ResponseEntity.ok(_adminService.getIncomeInTimeInterval(profitInInterval));
        } catch (EntityBadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/allIncomeRecords")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<IncomeRecordGetDto>> getAllIncomeRecords() {
        return ResponseEntity.ok(_adminService.getAllIncomeRecords().stream()
                .map(entity -> _mapper.map(entity, IncomeRecordGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/allLoyaltyProviderCategories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoyaltyProgramProviderGetDto>> getAllLoyaltyProviderCategories() {
        return ResponseEntity.ok(_adminService.getAllLoyaltyProviderCategories().stream()
                .map(entity -> _mapper.map(entity, LoyaltyProgramProviderGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/allLoyaltyClientCategories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoyaltyProgramClientGetDto>> getAllLoyaltyClientCategories() {
        return ResponseEntity.ok(_adminService.getAllLoyaltyClientCategories().stream()
                .map(entity -> _mapper.map(entity, LoyaltyProgramClientGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getCurrentSystemTax")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getCurrentSystemTax() {
        return ResponseEntity.ok(_adminService.getCurrentSystemTax());
    }

    @GetMapping(value="/getCurrentPointsProviderGetsAfterReservation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getCurrentPointsProviderGetsAfterReservation() {
        return ResponseEntity.ok(_adminService.getCurrentPointsProviderGetsAfterReservation());
    }

    @GetMapping(value="/getCurrentPointsClientGetsAfterReservation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getCurrentPointsClientGetsAfterReservation() {
        return ResponseEntity.ok(_adminService.getCurrentPointsClientGetsAfterReservation());
    }

    @GetMapping(value="/getAllCottageOwners")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CottageOwnerGet>> getAllCottageOwners() {
        return ResponseEntity.ok(_adminService.getAllCottageOwners().stream()
                .map(entity -> _mapper.map(entity, CottageOwnerGet.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getAllCottages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CottageGetDto>> getAllCottages() {
        return ResponseEntity.ok(_adminService.getAllCottages().stream()
                .map(entity -> _mapper.map(entity, CottageGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getAllBoatOwners")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BoatOwnerGet>> getAllBoatOwners() {
        return ResponseEntity.ok(_adminService.getAllBoatOwners().stream()
                .map(entity -> _mapper.map(entity, BoatOwnerGet.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getAllBoats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BoatGetDto>> getAllBoats() {
        return ResponseEntity.ok(_adminService.getAllBoats().stream()
                .map(entity -> _mapper.map(entity, BoatGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getAllFishingInstructors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FishingInstructorGetDto>> getAllFishingInstructors() {
        return ResponseEntity.ok(_adminService.getAllFishingInstructors().stream()
                .map(entity -> _mapper.map(entity, FishingInstructorGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getAllClients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ClientGetDto>> getAllClients() {
        return ResponseEntity.ok(_adminService.getAllClients().stream()
                .map(entity -> _mapper.map(entity, ClientGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getReservationsWithCommentariesForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationGetDto>> getReservationsWithCommentariesForAdmin() {
        return ResponseEntity.ok(_adminService.getReservationsWithCommentariesForAdmin().stream()
                .map(entity -> _mapper.map(entity, ReservationGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getQuickReservationsWithCommentariesForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuickReservationGetDto>> getQuickReservationsWithCommentariesForAdmin() {
        return ResponseEntity.ok(_adminService.getQuickReservationsWithCommentariesForAdmin().stream()
                .map(entity -> _mapper.map(entity, QuickReservationGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getFishingReservationsWithCommentariesForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FishingReservationGetDto>> getFishingReservationsWithCommentariesForAdmin() {
        return ResponseEntity.ok(_adminService.getFishingReservationsWithCommentariesForAdmin().stream()
                .map(entity -> _mapper.map(entity, FishingReservationGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getFishingQuickReservationsWithCommentariesForAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FishingQuickReservationGetDto>> getFishingQuickReservationsWithCommentariesForAdmin() {
        return ResponseEntity.ok(_adminService.getFishingQuickReservationsWithCommentariesForAdmin().stream()
                .map(entity -> _mapper.map(entity, FishingQuickReservationGetDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getAllComplaints")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintGet>> getAllComplaints() {
        return ResponseEntity.ok(_adminService.getAllComplaints().stream()
                .map(entity -> _mapper.map(entity, ComplaintGet.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getAllFishingInstructorComplaints")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintFishingInstructorGet>> getAllFishingInstructorComplaints() {
        return ResponseEntity.ok(_adminService.getAllFishingInstructorComplaints().stream()
                .map(entity -> _mapper.map(entity, ComplaintFishingInstructorGet.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getAllUnapprovedReviews")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReviewGet>> getAllUnapprovedReviews() {
        return ResponseEntity.ok(_adminService.getAllUnapprovedReviews().stream()
                .map(entity -> _mapper.map(entity, ReviewGet.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value="/getAllUnapprovedFishingTripReviews")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReviewFishingTripGet>> getAllUnapprovedFishingTripReviews() {
        return ResponseEntity.ok(_adminService.getAllUnapprovedFishingTripReviews().stream()
                .map(entity -> _mapper.map(entity, ReviewFishingTripGet.class))
                .collect(Collectors.toList()));
    }
}
