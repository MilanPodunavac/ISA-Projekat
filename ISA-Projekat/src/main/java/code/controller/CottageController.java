package code.controller;

import code.controller.base.BaseController;
import code.model.*;
import code.model.wrappers.DateRange;
import code.repository.CottageOwnerRepository;
import code.repository.CottageRepository;
import code.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

@RestController
@RequestMapping("/api/cottage")
public class CottageController extends BaseController {
    private final CottageOwnerRepository _cottageOwnerRepository;
    private final CottageRepository _cottageRepository;
    private final UserRepository _userRepository;

    public CottageController(ModelMapper mapper, CottageOwnerRepository cottageOwnerRepository, UserRepository userRepository, CottageRepository cottageRepository) {
        super(mapper);
        _cottageOwnerRepository = cottageOwnerRepository;
        _userRepository = userRepository;
        _cottageRepository = cottageRepository;
    }

    @PostMapping()
    public ResponseEntity<String> addOwner(){
        CottageOwner owner = new CottageOwner();
        owner.setEmail("dsasddasdsaads@gmail.com");
        _cottageOwnerRepository.save(owner);
        return ResponseEntity.ok("Asdsdadsa");
    }
    @PutMapping
    public ResponseEntity<String> addReservation(){
        Cottage cottage = _cottageRepository.findById(1).orElseGet(null);
        AvailabilityPeriod period = new AvailabilityPeriod();
        period.setRange(new DateRange(new GregorianCalendar(2022, Calendar.MAY, 1).getTime(), new GregorianCalendar(2022, Calendar.MAY, 30).getTime()));
        cottage.addAvailabilityPeriod(period);
        _cottageRepository.save(cottage);
        return ResponseEntity.ok("asasd");
    }
}
