package code.controller;

import code.controller.base.BaseController;
import code.dto.RegistrationRequest;
import code.service.FishingInstructorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fishing-instructor", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class FishingInstructorController extends BaseController {
    private final FishingInstructorService fishingInstructorService;

    @Autowired
    public FishingInstructorController(ModelMapper mapper, FishingInstructorService fishingInstructorService) {
        super(mapper);
        this.fishingInstructorService = fishingInstructorService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        if (fishingInstructorService.save(registrationRequest)) {
            return new ResponseEntity<>("Fishing instructor registered", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User with same email already exists", HttpStatus.CONFLICT);
        }
    }
}
