package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.boat.BoatGetDto;
import code.dto.entities.cottage.CottageGetDto;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.boat.Boat;
import code.model.cottage.Cottage;
import code.model.report.YearlyProfitReport;
import code.service.BoatOwnerService;
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
@RequestMapping("/api/boatOwner")
public class BoatOwnerController extends BaseController {

    private final BoatOwnerService _boatOwnerService;

    public BoatOwnerController(ModelMapper mapper, TokenUtils tokenUtils, BoatOwnerService boatOwnerService) {
        super(mapper, tokenUtils);
        _boatOwnerService = boatOwnerService;
    }

    @GetMapping(value = "cottages")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<Object> getOwnerCottages(){
        List<Boat> ownerBoats;
        try{
            ownerBoats = _boatOwnerService.getBoatOwnerBoats();
        }catch(Exception ex){
            if(ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops, something went wrong, try again");
        }
        ownerBoats.sort(Comparator.comparing(Boat::getId));
        return ResponseEntity.ok(_mapper.map(ownerBoats, new TypeToken<List<BoatGetDto>>() {}.getType()));
    }

    @GetMapping(value="profit")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<Object> getOwnerProfit(){
        try{
            YearlyProfitReport profit = _boatOwnerService.calculateYearlyProfitReport();
            return ResponseEntity.ok(profit);
        } catch (Exception e) {
            if(e instanceof UnauthorizedAccessException || e instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops, something went wrong, try again");
        }
    }

}
