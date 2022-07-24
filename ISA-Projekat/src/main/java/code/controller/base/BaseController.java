package code.controller.base;

import org.modelmapper.ModelMapper;

public class BaseController {
    protected final ModelMapper _mapper;

    public BaseController(ModelMapper mapper){
        _mapper = mapper;
    }
}
