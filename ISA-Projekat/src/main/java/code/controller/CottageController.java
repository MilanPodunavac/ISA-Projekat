package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.NewCottageDto;
import code.model.*;
import code.model.wrappers.DateRange;
import code.repository.CottageOwnerRepository;
import code.repository.CottageRepository;
import code.repository.UserRepository;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

@RestController
@RequestMapping("/api/cottage")
public class CottageController extends BaseController {
    private final CottageOwnerRepository _cottageOwnerRepository;
    private final CottageRepository _cottageRepository;
    private final UserRepository _userRepository;

    public CottageController(ModelMapper mapper, CottageOwnerRepository cottageOwnerRepository, UserRepository userRepository, CottageRepository cottageRepository, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        _cottageOwnerRepository = cottageOwnerRepository;
        _userRepository = userRepository;
        _cottageRepository = cottageRepository;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<String> addCottage(@Valid @RequestBody NewCottageDto dto, BindingResult result, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            Cottage cottage = _mapper.map(dto, Cottage.class);
            CottageOwner owner = (CottageOwner) _userRepository.findByEmail(email);
            if(owner == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cottage owner not found");
            owner.addCottage(cottage);
            _cottageRepository.save(cottage);
        }catch(Exception ex){
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Cottage added successfully");
    }
}
