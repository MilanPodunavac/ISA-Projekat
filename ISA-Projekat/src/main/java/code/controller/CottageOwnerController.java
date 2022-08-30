package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.cottage.CottageGetDto;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.cottage.Cottage;
import code.service.CottageOwnerService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/cottageOwner")
public class CottageOwnerController extends BaseController {

    private final CottageOwnerService _cottageOwnerService;

    public CottageOwnerController(ModelMapper mapper, TokenUtils tokenUtils, CottageOwnerService cottageOwnerService) {
        super(mapper, tokenUtils);
        _cottageOwnerService = cottageOwnerService;
    }

    @GetMapping(value = "cottages")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<Object> getOwnerCottages(){
        List<Cottage> ownerCottages;
        try{
            ownerCottages = _cottageOwnerService.getCottageOwnerCottages();
        }catch(Exception ex){
            if(ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops, something went wrong, try again");
        }
        ownerCottages.sort(Comparator.comparing(Cottage::getId));
        return ResponseEntity.ok(_mapper.map(ownerCottages, new TypeToken<List<CottageGetDto>>() {}.getType()));
    }
}
