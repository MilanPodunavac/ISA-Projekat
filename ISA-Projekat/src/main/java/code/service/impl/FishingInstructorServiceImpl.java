package code.service.impl;

import code.exceptions.registration.EmailTakenException;
import code.model.FishingInstructor;
import code.model.Role;
import code.repository.FishingInstructorRepository;
import code.service.FishingInstructorService;
import code.service.RoleService;
import code.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FishingInstructorServiceImpl implements FishingInstructorService {
    private final FishingInstructorRepository _fishingInstructorRepository;
    private final PasswordEncoder _passwordEncoder;
    private final RoleService _roleService;
    private final UserService _userService;

    public FishingInstructorServiceImpl(UserService userService, FishingInstructorRepository fishingInstructorRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this._userService = userService;
        this._fishingInstructorRepository = fishingInstructorRepository;
        this._passwordEncoder = passwordEncoder;
        this._roleService = roleService;
    }

    @Override
    public FishingInstructor save(FishingInstructor fishingInstructor) throws EmailTakenException {
        _userService.throwExceptionIfEmailExists(fishingInstructor.getEmail());
        return saveRegistrationRequest(fishingInstructor);
    }

    private FishingInstructor saveRegistrationRequest(FishingInstructor fishingInstructor) {
            fishingInstructor.setPassword(_passwordEncoder.encode(fishingInstructor.getPassword()));
            fishingInstructor.setEnabled(false);

            Role role = _roleService.findByName("ROLE_FISHING_INSTRUCTOR");
            fishingInstructor.setRole(role);

            return _fishingInstructorRepository.save(fishingInstructor);
    }
}