package code.controller.base;

import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class BaseController {
    protected final ModelMapper _mapper;

    protected final TokenUtils _tokenUtils;

    public BaseController(ModelMapper mapper, TokenUtils tokenUtils){
        _mapper = mapper;
        _tokenUtils = tokenUtils;
    }
    protected ResponseEntity<String> formatErrorResponse(BindingResult result) {
        String errors = new String();
        for(ObjectError error : result.getAllErrors()){
            errors += error.getDefaultMessage() + "\n";
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
