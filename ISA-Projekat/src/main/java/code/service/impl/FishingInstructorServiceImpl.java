package code.service.impl;

import code.model.FishingInstructor;
import code.model.Role;
import code.repository.FishingInstructorRepository;
import code.repository.UserRepository;
import code.service.FishingInstructorService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FishingInstructorServiceImpl implements FishingInstructorService {
    private final FishingInstructorRepository _fishingInstructorRepository;
    private final UserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;
    private final RoleService _roleService;

    public FishingInstructorServiceImpl(UserRepository userRepository, FishingInstructorRepository fishingInstructorRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this._userRepository = userRepository;
        this._fishingInstructorRepository = fishingInstructorRepository;
        this._passwordEncoder = passwordEncoder;
        this._roleService = roleService;
    }

    @Override
    public void save(FishingInstructor fishingInstructor) {
        fishingInstructor.setPassword(_passwordEncoder.encode(fishingInstructor.getPassword()));
        fishingInstructor.setEnabled(false);

        Role role = _roleService.findByName("ROLE_FISHING_INSTRUCTOR");
        fishingInstructor.setRole(role);

        _fishingInstructorRepository.save(fishingInstructor);
    }

}