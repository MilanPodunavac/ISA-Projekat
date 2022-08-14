package code.service.impl;

import code.exceptions.entities.EntityNotDeletableException;
import code.exceptions.entities.EntityNotFoundException;
import code.model.Boat;
import code.model.Cottage;
import code.repository.BoatRepository;
import code.repository.CottageRepository;
import code.repository.UserRepository;
import code.service.BoatService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoatServiceImpl  implements BoatService {
    private final BoatRepository _boatRepository;

    public BoatServiceImpl(BoatRepository boatRepository){
        _boatRepository = boatRepository;
    }

    @Override
    public void throwExceptionIfBoatDontExist(Integer id) throws EntityNotFoundException {
        Optional<Boat> boat = _boatRepository.findById(id);
        if (!boat.isPresent()) {
            throw new EntityNotFoundException("Boat doesn't exist!");
        }
    }

    @Override
    public void checkIfBoatDeletable(Integer id) throws EntityNotDeletableException {

    }

    @Override
    public void unlinkReferencesAndDeleteBoat(Integer id) {

    }
}
