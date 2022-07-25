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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> RegisterUser(RegistrationRequest dto){
        switch(dto.getUserType()){
            case BoatOwner:
                _boatOwnerService.save(_mapper.map(dto, BoatOwner.class));
            case CottageOwner:
                _cottageOwnerService.save(_mapper.map(dto, CottageOwner.class));
        }
        return ResponseEntity.ok("Registration request has been sent to admin to approve!");
    }
}
