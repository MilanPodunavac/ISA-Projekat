package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.AvailabilityPeriodGetDto;
import code.dto.entities.boat.BoatActionGetDto;
import code.dto.entities.boat.BoatGetDto;
import code.dto.entities.boat.BoatReservationGetDto;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.base.Action;
import code.model.base.AvailabilityPeriod;
import code.model.base.Reservation;
import code.model.boat.Boat;
import code.model.report.YearlyProfitReport;
import code.service.BoatOwnerService;
import code.service.BoatService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/boatOwner")
public class BoatOwnerController extends BaseController {

    private final BoatOwnerService _boatOwnerService;
    private final BoatService _boatService;
    public BoatOwnerController(ModelMapper mapper, TokenUtils tokenUtils, BoatOwnerService boatOwnerService, BoatService boatService) {
        super(mapper, tokenUtils);
        _boatOwnerService = boatOwnerService;
        _boatService = boatService;
    }

    @GetMapping(value = "boats")
    @PreAuthorize("hasRole('ROLE_BOAT_OWNER')")
    public ResponseEntity<Object> getOwnerBoats() throws EntityNotFoundException, IOException {
        List<Boat> ownerBoats;
        try{
            ownerBoats = _boatOwnerService.getBoatOwnerBoats();
        }catch(Exception ex){
            if(ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops, something went wrong, try again");
        }
        ownerBoats.sort(Comparator.comparing(Boat::getId));
        List<BoatGetDto> dtos = new ArrayList<>();
        for(Boat boat : ownerBoats){
            dtos.add(createBoatDto(boat));
        }
        return ResponseEntity.ok(dtos);
    }

    private BoatGetDto createBoatDto(Boat boat) throws EntityNotFoundException, IOException {
        BoatGetDto boatDto = _mapper.map(boat, BoatGetDto.class);
        boatDto.setDeletable(!boat.hasFutureReservationsOrActions());
        boatDto.setAvailabilityPeriods(new ArrayList<>());
        boatDto.setBoatReservations(new ArrayList<>());
        boatDto.setBoatActions(new ArrayList<>());
        for (AvailabilityPeriod period: boat.getAvailabilityPeriods()) {
            boatDto.getAvailabilityPeriods().add(new AvailabilityPeriodGetDto(period.getRange().getStartDate(), period.getRange().getEndDate()));
            for(Reservation res: period.getReservations()){
                BoatReservationGetDto dto = _mapper.map(res, BoatReservationGetDto.class);
                if(res.getOwnerCommentary() != null){
                    dto.setCommentary(res.getOwnerCommentary().getCommentary());
                }
                //dto.setClientFullName(res.getClient().getFirstName() + " " + res.getClient().getLastName());
                boatDto.getBoatReservations().add(dto);
            }
            for(Action act: period.getActions()){
                BoatActionGetDto dto = _mapper.map(act, BoatActionGetDto.class);
                if(act.getOwnerCommentary() != null){
                    dto.setCommentary(act.getOwnerCommentary().getCommentary());
                }
                if(act.getClient() != null){
                    //dto.setClientFullName(act.getClient().getFirstName() + " " + act.getClient().getLastName());
                    dto.setClientFirstName(act.getClient().getFirstName());
                    dto.setClientLastName(act.getClient().getLastName());
                }
                boatDto.getBoatActions().add(dto);
            }
        }
        boatDto.getAvailabilityPeriods().sort(Comparator.comparing(AvailabilityPeriodGetDto::getStartDate));
        boatDto.getBoatReservations().sort(Comparator.comparing(BoatReservationGetDto::getStartDate));
        boatDto.getBoatActions().sort(Comparator.comparing(BoatActionGetDto::getStartDate));
        boatDto.setPictures(_boatService.getBoatImagesAsBase64(boat.getId()));
        return boatDto;
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
