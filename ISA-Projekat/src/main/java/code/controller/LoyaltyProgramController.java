package code.controller;

import code.controller.base.BaseController;
import code.dto.fishing_instructor.FishingInstructorGetDto;
import code.dto.loyalty_program.LoyaltyProgramProviderGetDto;
import code.exceptions.entities.EntityNotFoundException;
import code.model.LoyaltyProgramProvider;
import code.service.FishingInstructorService;
import code.service.LoyaltyProgramService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loyalty-program")
public class LoyaltyProgramController extends BaseController {
    private final LoyaltyProgramService _loyaltyProgramService;

    public LoyaltyProgramController(LoyaltyProgramService loyaltyProgramService, ModelMapper mapper, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        this._loyaltyProgramService = loyaltyProgramService;
    }

    @GetMapping(value = "/getOneHigherLoyaltyProviderCategory/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','FISHING_INSTRUCTOR','COTTAGE_OWNER','BOAT_OWNER')")
    public ResponseEntity<LoyaltyProgramProviderGetDto> getOneHigherLoyaltyProviderCategory(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(_mapper.map(_loyaltyProgramService.getOneHigherLoyaltyCategory(id), LoyaltyProgramProviderGetDto.class));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
