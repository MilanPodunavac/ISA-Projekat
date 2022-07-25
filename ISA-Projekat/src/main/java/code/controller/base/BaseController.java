package code.controller.base;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class BaseController {
    protected final ModelMapper _mapper;

    public BaseController(ModelMapper mapper){
        _mapper = mapper;
    }
    protected ResponseEntity<String> formatErrorResponse(BindingResult result) {
        String errors = new String();
        for(ObjectError error : result.getAllErrors()){
            errors += error.getDefaultMessage() + "\n";
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
