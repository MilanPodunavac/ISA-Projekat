package code.controller;

import code.controller.base.BaseController;
import code.dto.admin.*;
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

@RestController
@RequestMapping("/api/admin")
public class AdminController extends BaseController {
    private final AdminService _adminService;

    public AdminController(AdminService adminService, ModelMapper mapper, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        this._adminService = adminService;
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

    @PutMapping(value = "/fishingReservation/{reservationId}/commentaryAccept", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(value = "/fishingReservation/{reservationId}/commentaryDecline", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping(value = "/fishingQuickReservation/{quickReservationId}/commentaryAccept", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> fishingQuickReservationCommentaryAccept(@PathVariable Integer quickReservationId) {
        try {
            _adminService.fishingQuickReservationCommentaryAccept(quickReservationId);
            return ResponseEntity.ok("Fishing reservation commentary accepted!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/fishingQuickReservation/{quickReservationId}/commentaryDecline", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> fishingQuickReservationCommentaryDecline(@PathVariable Integer quickReservationId) {
        try {
            _adminService.fishingQuickReservationCommentaryDecline(quickReservationId);
            return ResponseEntity.ok("Fishing reservation commentary declined!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/reservation/{reservationId}/commentaryAccept", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> reservationCommentaryAccept(@PathVariable Integer reservationId) {
        try {
            _adminService.reservationCommentaryAccept(reservationId);
            return ResponseEntity.ok("Fishing reservation commentary accepted!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/reservation/{reservationId}/commentaryDecline", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> reservationCommentaryDecline(@PathVariable Integer reservationId) {
        try {
            _adminService.reservationCommentaryDecline(reservationId);
            return ResponseEntity.ok("Fishing reservation commentary declined!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/quickReservation/{quickReservationId}/commentaryAccept", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> quickReservationCommentaryAccept(@PathVariable Integer quickReservationId) {
        try {
            _adminService.quickReservationCommentaryAccept(quickReservationId);
            return ResponseEntity.ok("Fishing reservation commentary accepted!");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CommentaryNotApprovableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/quickReservation/{quickReservationId}/commentaryDecline", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> quickReservationCommentaryDecline(@PathVariable Integer quickReservationId) {
        try {
            _adminService.quickReservationCommentaryDecline(quickReservationId);
            return ResponseEntity.ok("Fishing reservation commentary declined!");
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
}
