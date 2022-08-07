package code.service.impl;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.repository.CottageRepository;
import code.repository.UserRepository;
import code.service.CottageService;
import org.springframework.mail.SimpleMailMessage;
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
    public void addCottage(String email, Cottage cottage) throws UserNotFoundException, UnauthorizedAccessException {
        CottageOwner owner;
        try{
            owner = (CottageOwner) _userRepository.findByEmail(email);
        }catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a cottage owner");
        }
        if(owner == null) throw new UserNotFoundException("Cottage owner not found");
        owner.addCottage(cottage);
        _cottageRepository.save(cottage);
    }

    @Override
    public void addAvailabilityPeriod(int cottageId, AvailabilityPeriod period, String email) throws AvailabilityPeriodBadRangeException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException {
        CottageOwner owner;
        try{
            owner = (CottageOwner) _userRepository.findByEmail(email);
        }catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a cottage owner");
        }
        if(owner == null)throw new UnauthorizedAccessException("Cottage owner not found");
        Optional<Cottage> optionalCottage = _cottageRepository.findById(cottageId);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(cottage.getCottageOwner().getId() != owner.getId())throw new EntityNotOwnedException("Cottage not owned by given user");
        cottage.addAvailabilityPeriod(period);
        _cottageRepository.save(cottage);
    }

    @Override
    public void addReservation(String clientEmail, int cottageId, CottageReservation reservation, String email) throws EntityNotFoundException, UserNotFoundException, InvalidReservationException, EntityNotOwnedException, EntityNotAvailableException, UnauthorizedAccessException {
        CottageOwner owner;
        try{
             owner = (CottageOwner) _userRepository.findByEmail(email);
        }catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a cottage owner");
        }
        if(owner == null)throw new UnauthorizedAccessException("Cottage owner not found");
        Client client;
        try{
            client = (Client) _userRepository.findByEmail(clientEmail);
        }catch(ClassCastException ex){
            throw new UserNotFoundException("User is not a client");
        }
        if(client == null)throw new UserNotFoundException("Client not found");
        Optional<Cottage> optionalCottage = _cottageRepository.findById(cottageId);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(cottage.getCottageOwner().getId() != owner.getId())throw new EntityNotOwnedException("Cottage not owned by given user");
        reservation.setClient(client);
        if(!cottage.addReservation(reservation))throw new EntityNotAvailableException("Cottage is not available at the given time");
        _cottageRepository.save(cottage);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(client.getEmail());
        message.setSubject("Cottage reserved");
        message.setText("Cottage " + cottage.getName() + " has been successfully reserved" );
        _mailSender.send(message);
    }

}
