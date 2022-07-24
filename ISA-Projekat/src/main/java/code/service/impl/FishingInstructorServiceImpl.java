package code.service.impl;

import code.dto.RegistrationRequest;
import code.model.CottageOwner;
import code.model.FishingInstructor;
import code.model.Location;
import code.model.Role;
import code.repository.FishingInstructorRepository;
import code.service.FishingInstructorService;
import code.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FishingInstructorServiceImpl implements FishingInstructorService {

    @Autowired
    private FishingInstructorRepository fishingInstructorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public FishingInstructor findByEmail(String email) throws UsernameNotFoundException {
        return fishingInstructorRepository.findByEmail(email);
    }

    public FishingInstructor findById(Integer id) {
        return fishingInstructorRepository.findById(id).orElseGet(null);
    }

    public List<FishingInstructor> findAll() {
        return fishingInstructorRepository.findAll();
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