package code.controller;

import code.controller.base.BaseController;
import code.dto.provider_registration.DeclineRegistrationRequestDTO;
import code.dto.user.AccountDeletionRequestDto;
import code.dto.user.AccountDeletionResponse;
import code.dto.user.UpdatePasswordDto;
import code.dto.user.UpdateUserPersonalInfoDto;
import code.exceptions.entities.AccountDeletionRequestDontExistException;
import code.exceptions.provider_registration.NotProviderException;
import code.exceptions.provider_registration.UserAccountActivatedException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.AccountDeletionRequest;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(value = "/accountDeletionRequest", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('CLIENT','FISHING_INSTRUCTOR','COTTAGE_OWNER','BOAT_OWNER')")
    public ResponseEntity<String> submitAccountDeletionRequest(@Valid @RequestBody AccountDeletionRequestDto dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        _userService.submitAccountDeletionRequest(_mapper.map(dto, AccountDeletionRequest.class));
        return ResponseEntity.ok("Account deletion request submitted!");
    }

    @DeleteMapping(value = "/declineAccountDeletionRequest/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> declineAccountDeletionRequest(@PathVariable Integer id, @Valid @RequestBody AccountDeletionResponse dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _userService.declineAccountDeletionRequest(id, dto.getResponseText());
            return ResponseEntity.ok("Account deletion request declined: " + dto.getResponseText());
        } catch (AccountDeletionRequestDontExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/acceptAccountDeletionRequest/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> acceptAccountDeletionRequest(@PathVariable Integer id, @Valid @RequestBody AccountDeletionResponse dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        try {
            _userService.acceptAccountDeletionRequest(id, dto.getResponseText());
            return ResponseEntity.ok("Account deletion request accepted: " + dto.getResponseText());
        } catch (AccountDeletionRequestDontExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
