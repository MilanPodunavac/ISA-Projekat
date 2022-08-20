package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.boat.BoatDto;
import code.dto.entities.cottage.CottageDto;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.boat.Boat;
import code.repository.BoatOwnerRepository;
import code.repository.BoatRepository;
import code.repository.UserRepository;
import code.service.BoatService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/boat")
public class BoatController extends BaseController {
    private final BoatService _boatService;
    private final BoatRepository _boatRepository;
    private final BoatOwnerRepository _boatOwnerRepository;
    private final UserRepository _userRepository;

    public BoatController(ModelMapper mapper, TokenUtils tokenUtils, BoatService boatService, BoatRepository boatRepository, BoatOwnerRepository boatOwnerRepository, UserRepository userRepository) {
        super(mapper, tokenUtils);
        _boatService = boatService;
        _boatRepository = boatRepository;
        _boatOwnerRepository = boatOwnerRepository;
        _userRepository = userRepository;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<String> addBoat(@Valid @RequestBody BoatDto dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);//400
        }
        try{
            _boatService.addBoat(_mapper.map(dto, Boat.class));
        }catch(Exception ex){
            if(ex instanceof UserNotFoundException || ex instanceof UnauthorizedAccessException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cottage owner not found");
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Boat added successfully");
    }
}
