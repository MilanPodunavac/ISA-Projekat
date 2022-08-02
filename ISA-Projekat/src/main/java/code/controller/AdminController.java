package code.controller;

import code.controller.base.BaseController;
import code.dto.AdminRegistration;
import code.dto.PasswordDTO;
import code.dto.PersonalData;
import code.exceptions.admin.ChangedPasswordException;
import code.exceptions.admin.ModifyAnotherUserDataException;
import code.exceptions.admin.NonMainAdminRegisterOtherAdminException;
import code.exceptions.admin.NotChangedPasswordException;
import code.exceptions.registration.EmailTakenException;
import code.exceptions.registration.UserNotFoundException;
import code.model.Admin;
import code.service.*;
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
    private final UserService _userService;

    public AdminController(AdminService adminService, UserService userService, ModelMapper mapper) {
        super(mapper);
        this._adminService = adminService;
        this._userService = userService;
    }

    @PutMapping(value = "/changePersonalData", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changePersonalData(@Valid @RequestBody PersonalData dto, BindingResult result) throws UserNotFoundException, ModifyAnotherUserDataException {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _adminService.changePersonalData(_mapper.map(dto, Admin.class));
            return ResponseEntity.ok("Personal data changed!");
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ModifyAnotherUserDataException | NotChangedPasswordException e) {
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
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ModifyAnotherUserDataException | ChangedPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
