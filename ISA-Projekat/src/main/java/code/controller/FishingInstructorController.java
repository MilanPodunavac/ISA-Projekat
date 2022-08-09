package code.controller;

import code.controller.base.BaseController;
import code.dto.fishing_instructor.AddAvailablePeriod;
import code.exceptions.fishing_instructor.AddAvailablePeriodInPastException;
import code.exceptions.fishing_instructor.AvailablePeriodOverlappingException;
import code.exceptions.fishing_instructor.AvailablePeriodStartAfterEndDateException;
import code.model.FishingInstructorAvailablePeriod;
import code.service.FishingInstructorService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fishing-instructor")
public class FishingInstructorController extends BaseController {
    private final FishingInstructorService _fishingInstructorService;

    public FishingInstructorController(FishingInstructorService fishingInstructorService, ModelMapper mapper, TokenUtils tokenUtils) {
        super(mapper, tokenUtils);
        this._fishingInstructorService = fishingInstructorService;
    }

    @PostMapping(value = "/addAvailablePeriod", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('FISHING_INSTRUCTOR')")
    public ResponseEntity<String> addAvailablePeriod(@Valid @RequestBody AddAvailablePeriod dto, BindingResult result) {
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
}
