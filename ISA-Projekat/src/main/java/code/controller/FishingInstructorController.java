package code.controller;

import code.controller.base.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fishing-instructor")
@CrossOrigin(origins = "http://localhost:4200")
public class FishingInstructorController extends BaseController {
    public FishingInstructorController(ModelMapper mapper) {
        super(mapper);
    }
}
