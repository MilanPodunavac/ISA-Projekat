package code.controller;

import code.controller.base.BaseController;
import code.dto.admin.AdminRegistration;
import code.dto.admin.PasswordDTO;
import code.dto.admin.PersonalData;
import code.exceptions.admin.*;
import code.exceptions.entities.EntityNotDeletableException;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.entities.UnexpectedUserRoleException;
import code.exceptions.provider_registration.EmailTakenException;
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

    public AdminController(AdminService adminService, UserService userService, ModelMapper mapper, TokenUtils tokenUtils) {
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
        }
    }
}
