package code.controller;

import code.controller.base.BaseController;
import code.dto.ProviderRegistrationRequest;
import code.model.BoatOwner;
import code.model.CottageOwner;
import code.model.FishingInstructor;
import code.model.User;
import code.service.BoatOwnerService;
import code.service.CottageOwnerService;
import code.service.FishingInstructorService;
import code.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController extends BaseController {
    private final UserService _userService;
    private final CottageOwnerService _cottageOwnerService;
    private final BoatOwnerService _boatOwnerService;
    private final FishingInstructorService _fishingInstructorService;

    public RegistrationController(UserService userService, CottageOwnerService cottageOwnerService, BoatOwnerService boatOwnerService, FishingInstructorService fishingInstructorService, ModelMapper mapper) {
        super(mapper);
        this._userService = userService;
        this._cottageOwnerService = cottageOwnerService;
        this._boatOwnerService = boatOwnerService;
        this._fishingInstructorService = fishingInstructorService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody ProviderRegistrationRequest dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        if(_userService.userExists(dto.getEmail())) {
            return new ResponseEntity<>("User with entered email already exists!", HttpStatus.CONFLICT);
        }

        switch(dto.getProviderType()){
            case "BoatOwner":
                _boatOwnerService.save(_mapper.map(dto, BoatOwner.class));
                break;
            case "CottageOwner":
                _cottageOwnerService.save(_mapper.map(dto, CottageOwner.class));
                break;
            case "FishingInstructor":
                _fishingInstructorService.save(_mapper.map(dto, FishingInstructor.class));
                break;
            default:

        }
        return ResponseEntity.ok("Registration request has been sent to admin to approve!");
    }

    @GetMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getRegistrationRequests(){
        return ResponseEntity.ok(_userService.getUnverifiedProviders());
    }

    @PutMapping(value = "/accept-request/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> acceptRegistrationRequest(@PathVariable String email){
        _userService.acceptRegistrationRequest(email);
        return ResponseEntity.ok("Registration request accepted!");
    }

    @DeleteMapping(value = "/decline-request/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> declineRegistrationRequest(@PathVariable String email, @RequestBody String declineReason){
        _userService.declineRegistrationRequest(email, declineReason);
        return ResponseEntity.ok("Registration request declined: " + declineReason);
    }
}
