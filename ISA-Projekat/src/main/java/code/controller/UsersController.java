package code.controller;

import code.controller.base.BaseController;
import code.dto.user.UpdatePasswordDto;
import code.dto.user.UpdateUserPersonalInfoDto;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.Cottage;
import code.model.CottageOwner;
import code.model.User;
import code.repository.UserRepository;
import code.service.UserService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UsersController extends BaseController {

    private final UserService _userService;

    public UsersController(ModelMapper mapper, TokenUtils tokenUtils, UserService userService) {
        super(mapper, tokenUtils);
        _userService = userService;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUserPersonalInfo(@Valid @RequestBody UpdateUserPersonalInfoDto dto, BindingResult result, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            User user = _mapper.map(dto, CottageOwner.class);
            user.setEmail(email);
            _userService.updatePersonalInformation(user);
        }catch(Exception ex){
            if(ex instanceof UserNotFoundException){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            }
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("Personal information updated");
    }

    @PutMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto, BindingResult result, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }
        if(!updatePasswordDto.getNewPassword().equals(updatePasswordDto.getRepeatedNewPassword()))return ResponseEntity.badRequest().body("Passwords do not match");
        try{
            String email = _tokenUtils.getEmailFromToken(auth.substring(7));
            _userService.changePassword(updatePasswordDto.getNewPassword(), email);
        }catch(Exception ex){
            if(ex instanceof UserNotFoundException){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
            }
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
        return ResponseEntity.ok("asd");
    }
}
