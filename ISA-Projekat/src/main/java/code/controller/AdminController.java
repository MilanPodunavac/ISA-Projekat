package code.controller;

import code.controller.base.BaseController;
import code.dto.PersonalData;
import code.model.Admin;
import code.model.User;
import code.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController extends BaseController {
    private final AdminService _adminService;

    public AdminController(AdminService adminService, ModelMapper mapper) {
        super(mapper);
        this._adminService = adminService;
    }

    @PutMapping(value = "/changePersonalData", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changePersonalData(@Valid @RequestBody PersonalData dto, BindingResult result){
        if(result.hasErrors()){
            return formatErrorResponse(result);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (user.getId() != dto.getId()) {
            return new ResponseEntity<>("You can't modify another admin personal data!", HttpStatus.UNAUTHORIZED);
        }

        _adminService.changePersonalData(_mapper.map(dto, Admin.class));
        return ResponseEntity.ok("Personal data changed!");
    }
}
