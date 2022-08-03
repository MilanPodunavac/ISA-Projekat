package code.controller;

import code.controller.base.BaseController;
import code.dto.auth.LoginRequest;
import code.dto.auth.UserTokenState;
import code.model.User;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
    private final AuthenticationManager _authenticationManager;

    public AuthController(ModelMapper mapper, TokenUtils tokenUtils, AuthenticationManager authenticationManager) {
        super(mapper, tokenUtils);
        this._authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = _tokenUtils.generateToken(user.getEmail());
        int expiresIn = _tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }
}
