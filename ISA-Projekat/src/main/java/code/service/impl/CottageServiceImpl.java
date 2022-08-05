package code.service.impl;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.repository.CottageRepository;
import code.repository.UserRepository;
import code.service.CottageService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CottageServiceImpl implements CottageService {

    private final UserRepository _userRepository;

    private final CottageRepository _cottageRepository;

    private final JavaMailSender _mailSender;

    public CottageServiceImpl(UserRepository userRepository, CottageRepository cottageRepository, JavaMailSender mailSender){
        _cottageRepository = cottageRepository;
        _userRepository = userRepository;
        _mailSender = mailSender;
    }

    @Override
    public void addCottage(String email, Cottage cottage) throws UserNotFoundException {
        CottageOwner owner = (CottageOwner) _userRepository.findByEmail(email);
        if(owner == null) throw new UserNotFoundException("Cottage owner not found");
        owner.addCottage(cottage);
        _cottageRepository.save(cottage);
    }

    @Override
    public void addAvailabilityPeriod(int cottageId, AvailabilityPeriod period, String email) throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotFoundException, EntityNotOwnedException {
        CottageOwner owner = (CottageOwner) _userRepository.findByEmail(email);
        if(owner == null)throw new UserNotFoundException("Cottage owner not found");
        Optional<Cottage> optionalCottage = _cottageRepository.findById(cottageId);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(cottage.getCottageOwner().getId() != owner.getId())throw new EntityNotOwnedException("Cottage not owned by given user");
        cottage.addAvailabilityPeriod(period);
        _cottageRepository.save(cottage);
    }

    @Override
    public void addReservation(String clientEmail, int cottageId, CottageReservation reservation, String email) throws EntityNotFoundException, UserNotFoundException, InvalidReservationException, EntityNotOwnedException, EntityNotAvailableException {
        CottageOwner owner = (CottageOwner) _userRepository.findByEmail(email);
        if(owner == null)throw new UserNotFoundException("Cottage owner not found");
        Client client = (Client) _userRepository.findByEmail(clientEmail);
        if(client == null)throw new UserNotFoundException("Client not found");
        Optional<Cottage> optionalCottage = _cottageRepository.findById(cottageId);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(cottage.getCottageOwner().getId() != owner.getId())throw new EntityNotOwnedException("Cottage not owned by given user");
        reservation.setClient(client);
        if(cottage.addReservation(reservation) == false)throw new EntityNotAvailableException("Cottage is not available at the given time");
        _cottageRepository.save(cottage);
    }

}
