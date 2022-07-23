package code.controller.base;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    protected final ModelMapper _mapper;

    public BaseController(ModelMapper mapper){
        _mapper = mapper;
    }
}
