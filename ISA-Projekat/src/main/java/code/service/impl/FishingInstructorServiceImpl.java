package code.service.impl;

import code.dto.RegistrationRequest;
import code.model.FishingInstructor;
import code.model.Location;
import code.model.Role;
import code.repository.FishingInstructorRepository;
import code.service.FishingInstructorService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FishingInstructorServiceImpl implements FishingInstructorService {
    private final FishingInstructorRepository fishingInstructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public FishingInstructorServiceImpl(FishingInstructorRepository fishingInstructorRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.fishingInstructorRepository = fishingInstructorRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public FishingInstructor save(RegistrationRequest registrationRequest) {
        FishingInstructor fi = new FishingInstructor();
        Location l = new Location();
        fi.setEmail(registrationRequest.getEmail());
        fi.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        fi.setFirstName(registrationRequest.getFirstName());
        fi.setLastName(registrationRequest.getLastName());
        fi.setPhoneNumber(registrationRequest.getPhoneNumber());
        l.setStreetName(registrationRequest.getAddress());
        l.setCityName(registrationRequest.getCity());
        l.setCountryName(registrationRequest.getCountry());
        l.setLatitude(0);
        l.setLongitude(0);
        fi.setLocation(l);
        fi.setEnabled(false);
        fi.setReasonForRegistration(registrationRequest.getReasonForRegistration());
        fi.setBiography(registrationRequest.getBiography());

        Role role = roleService.findByName("ROLE_FISHING_INSTRUCTOR");
        fi.setRole(role);

        return this.fishingInstructorRepository.save(fi);
    }

}