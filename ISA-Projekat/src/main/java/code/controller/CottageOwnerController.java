package code.controller;

import code.controller.base.BaseController;
import code.dto.entities.AvailabilityPeriodGetDto;
import code.dto.entities.cottage.CottageActionGetDto;
import code.dto.entities.cottage.CottageGetDto;
import code.dto.entities.cottage.CottageReservationGetDto;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.base.Action;
import code.model.base.AvailabilityPeriod;
import code.model.base.Reservation;
import code.model.cottage.Cottage;
import code.model.report.YearlyProfitReport;
import code.service.CottageOwnerService;
import code.service.CottageService;
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
@RequestMapping("/api/cottageOwner")
public class CottageOwnerController extends BaseController {

    private final CottageOwnerService _cottageOwnerService;
    private final CottageService _cottageService;

    public CottageOwnerController(ModelMapper mapper, TokenUtils tokenUtils, CottageOwnerService cottageOwnerService, CottageService cottageService) {
        super(mapper, tokenUtils);
        _cottageOwnerService = cottageOwnerService;
        _cottageService = cottageService;
    }

    @GetMapping(value = "cottages")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<Object> getOwnerCottages() throws EntityNotFoundException, IOException {
        List<Cottage> ownerCottages;
        try{
            ownerCottages = _cottageOwnerService.getCottageOwnerCottages();
        }catch(Exception ex){
            if(ex instanceof UnauthorizedAccessException || ex instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops, something went wrong, try again");
        }
        ownerCottages.sort(Comparator.comparing(Cottage::getId));
        List<CottageGetDto> dtos = new ArrayList<>();
        for(Cottage cottage : ownerCottages){
            dtos.add(createCottageGetDto(cottage));
        }
        return ResponseEntity.ok(dtos);
    }

    private CottageGetDto createCottageGetDto(Cottage cottage) throws EntityNotFoundException, IOException {
        CottageGetDto cottageDto = _mapper.map(cottage, CottageGetDto.class);
        cottageDto.setDeletable(!cottage.hasFutureReservationsOrActions());
        cottageDto.setAvailabilityPeriods(new ArrayList<>());
        cottageDto.setCottageReservations(new ArrayList<>());
        cottageDto.setCottageActions(new ArrayList<>());
        for (AvailabilityPeriod period: cottage.getAvailabilityPeriods()) {
            cottageDto.getAvailabilityPeriods().add(new AvailabilityPeriodGetDto(period.getRange().getStartDate(), period.getRange().getEndDate()));
            for(Reservation res: period.getReservations()){
                CottageReservationGetDto dto = _mapper.map(res, CottageReservationGetDto.class);
                if(res.getOwnerCommentary() != null){
                    dto.setCommentary(res.getOwnerCommentary().getCommentary());
                }
                dto.setClientFullName(res.getClient().getFirstName() + " " + res.getClient().getLastName());
                cottageDto.getCottageReservations().add(dto);
            }
            for(Action act: period.getActions()){
                CottageActionGetDto dto = _mapper.map(act, CottageActionGetDto.class);
                if(act.getOwnerCommentary() != null){
                    dto.setCommentary(act.getOwnerCommentary().getCommentary());
                }
                if(act.getClient() != null){
                    dto.setClientFullName(act.getClient().getFirstName() + " " + act.getClient().getLastName());
                }
                cottageDto.getCottageActions().add(dto);
            }
        }
        cottageDto.getAvailabilityPeriods().sort(Comparator.comparing(AvailabilityPeriodGetDto::getStartDate));
        cottageDto.getCottageReservations().sort(Comparator.comparing(CottageReservationGetDto::getStartDate));
        cottageDto.getCottageActions().sort(Comparator.comparing(CottageActionGetDto::getStartDate));
        cottageDto.setPictures(_cottageService.getCottageImagesAsBase64(cottage.getId()));
        return cottageDto;
    }

    @GetMapping(value="profit")
    @PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<Object> getOwnerProfit(){
        try{
            YearlyProfitReport profit = _cottageOwnerService.calculateYearlyProfitReport();
            return ResponseEntity.ok(profit);
        } catch (Exception e) {
            if(e instanceof UnauthorizedAccessException || e instanceof UserNotFoundException)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops, something went wrong, try again");
        }
    }
}
