package code.controller;

import code.controller.base.BaseController;
import code.dto.provider_registration.DeclineRegistrationRequestDTO;
import code.dto.provider_registration.ProviderDTO;
import code.dto.provider_registration.ProviderRegistrationRequest;
import code.exceptions.provider_registration.EmailTakenException;
import code.exceptions.provider_registration.NotProviderException;
import code.exceptions.provider_registration.UserAccountActivatedException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.boat.BoatOwner;
import code.model.cottage.CottageOwner;
import code.model.FishingInstructor;
import code.service.BoatOwnerService;
import code.service.CottageOwnerService;
import code.service.FishingInstructorService;
import code.service.UserService;
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
@RequestMapping("/api/registration")
public class ProviderRegistrationController extends BaseController {
    private final UserService _userService;
    private final CottageOwnerService _cottageOwnerService;
    private final BoatOwnerService _boatOwnerService;
    private final FishingInstructorService _fishingInstructorService;

    public ProviderRegistrationController(UserService userService, CottageOwnerService cottageOwnerService, BoatOwnerService boatOwnerService, FishingInstructorService fishingInstructorService, ModelMapper mapper, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        this._userService = userService;
        this._cottageOwnerService = cottageOwnerService;
        this._boatOwnerService = boatOwnerService;
        this._fishingInstructorService = fishingInstructorService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody ProviderRegistrationRequest dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            switch (dto.getProviderType()) {
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
        } catch (EmailTakenException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProviderDTO>> getRegistrationRequests(){
        return ResponseEntity.ok(_userService.getUnverifiedProviders().stream()
                .map(entity -> _mapper.map(entity, ProviderDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/accept-request/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> acceptRegistrationRequest(@PathVariable Integer id){
        try {
            _userService.acceptRegistrationRequest(id);
            return ResponseEntity.ok("Registration request accepted!");
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserAccountActivatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotProviderException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/decline-request/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> declineRegistrationRequest(@PathVariable Integer id, @Valid @RequestBody DeclineRegistrationRequestDTO declineReason, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _userService.declineRegistrationRequest(id, declineReason.getDeclineReason());
            return ResponseEntity.ok("Registration request declined: " + declineReason.getDeclineReason());
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserAccountActivatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotProviderException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
