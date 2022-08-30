package code.controller;

import code.controller.base.BaseController;
import code.dto.admin.PasswordDTO;
import code.dto.admin.PersonalData;
import code.dto.fishing_instructor.FishingInstructorGetDto;
import code.dto.fishing_instructor.NewAvailablePeriod;
import code.dto.loyalty_program.LoyaltyProgramProviderGetDto;
import code.exceptions.entities.EntityNotFoundException;
import code.exceptions.fishing_instructor.AddAvailablePeriodInPastException;
import code.exceptions.fishing_instructor.AvailablePeriodOverlappingException;
import code.exceptions.fishing_instructor.AvailablePeriodStartAfterEndDateException;
import code.model.FishingInstructor;
import code.model.FishingInstructorAvailablePeriod;
import code.service.FishingInstructorService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/fishing-instructor")
public class FishingInstructorController extends BaseController {
    private final FishingInstructorService _fishingInstructorService;

    public FishingInstructorController(FishingInstructorService fishingInstructorService, ModelMapper mapper, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        this._fishingInstructorService = fishingInstructorService;
    }
    @GetMapping()
    public ResponseEntity<List<Object>> get(){
        return ResponseEntity.ok(_mapper.map(_fishingInstructorService.getAllFishingInstructors(), new TypeToken<List<FishingInstructorGetDto>>() {}.getType()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id){
        try{
            FishingInstructor fishingInstructor = _fishingInstructorService.getFishingInstructor(id);
            FishingInstructorGetDto fishingInstructorGetDto = _mapper.map(fishingInstructor, FishingInstructorGetDto.class);
            return ResponseEntity.ok(fishingInstructorGetDto);
        }catch(Exception ex){
            if(ex instanceof EntityNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fishing instructor not found");
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
    }

    @PostMapping(value = "/addAvailablePeriod", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> addAvailablePeriod(@Valid @RequestBody NewAvailablePeriod dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _fishingInstructorService.addAvailablePeriod(_mapper.map(dto, FishingInstructorAvailablePeriod.class));
            return ResponseEntity.ok("Available period added!");
        } catch (AvailablePeriodStartAfterEndDateException | AddAvailablePeriodInPastException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AvailablePeriodOverlappingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/changePersonalData", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> changePersonalData(@Valid @RequestBody PersonalData dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        _fishingInstructorService.changePersonalData(_mapper.map(dto, FishingInstructor.class));
        return ResponseEntity.ok("Personal data changed!");
    }

    @PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordDTO dto, BindingResult result) {
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        _fishingInstructorService.changePassword(_mapper.map(dto, FishingInstructor.class));
        return ResponseEntity.ok("Password changed!");
    }

    @GetMapping(value = "/getLoggedInInstructor")
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<FishingInstructorGetDto> getLoggedInInstructor() {
        return ResponseEntity.ok(_mapper.map(_fishingInstructorService.getLoggedInInstructor(), FishingInstructorGetDto.class));
    }
}
