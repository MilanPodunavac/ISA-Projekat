package code.controller;

import code.controller.base.BaseController;
import code.dto.RegistrationRequest;
import code.model.BoatOwner;
import code.model.CottageOwner;
import code.service.BoatOwnerService;
import code.service.CottageOwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController extends BaseController {

    private final CottageOwnerService _cottageOwnerService;
    private final BoatOwnerService _boatOwnerService;

    public RegistrationController(CottageOwnerService cottageOwnerService, BoatOwnerService boatOwnerService, ModelMapper mapper) {
        super(mapper);
        _cottageOwnerService = cottageOwnerService;
        _boatOwnerService = boatOwnerService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> RegisterUser(@Valid @RequestBody RegistrationRequest dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }
        switch(dto.getUserType()){
            case BoatOwner:
                _boatOwnerService.save(_mapper.map(dto, BoatOwner.class));
                break;
            case CottageOwner:
                _cottageOwnerService.save(_mapper.map(dto, CottageOwner.class));
                break;
            default:

        }
        return ResponseEntity.ok("Registration request has been sent to admin to approve!");
    }
}
