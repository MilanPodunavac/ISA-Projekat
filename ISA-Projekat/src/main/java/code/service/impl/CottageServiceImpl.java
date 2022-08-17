package code.service.impl;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.model.base.AvailabilityPeriod;
import code.model.base.Picture;
import code.model.base.Reservation;
import code.model.cottage.Cottage;
import code.model.cottage.CottageAction;
import code.model.cottage.CottageOwner;
import code.model.cottage.CottageReservation;
import code.repository.CottageRepository;
import code.repository.PictureRepository;
import code.repository.ReservationRepository;
import code.repository.UserRepository;
import code.service.CottageService;
import code.utils.FileUploadUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class CottageServiceImpl implements CottageService {

    private final String COTTAGE_PICTURE_DIRECTORY = "cottage_images";

    private final UserRepository _userRepository;

    private final CottageRepository _cottageRepository;
    private final ReservationRepository _reservationRepository;
    private final PictureRepository _pictureRepository;

    private final JavaMailSender _mailSender;

    public CottageServiceImpl(UserRepository userRepository, CottageRepository cottageRepository, ReservationRepository reservationRepository, PictureRepository pictureRepository, JavaMailSender mailSender){
        _cottageRepository = cottageRepository;
        _userRepository = userRepository;
        _reservationRepository = reservationRepository;
        _pictureRepository = pictureRepository;
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
        if(!client.isAvailable(reservation.getDateRange()))throw new EntityNotAvailableException("Client already has reservation at the given time");
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

    @Override
    public void throwExceptionIfCottageDontExist(Integer id) throws EntityNotFoundException {
        Optional<Cottage> cottage = _cottageRepository.findById(id);
        if (!cottage.isPresent()) {
            throw new EntityNotFoundException("Cottage doesn't exist!");
        }
    }

    @Override
    public void checkIfCottageDeletable(Integer id) throws EntityNotDeletableException, EntityNotFoundException {
        Optional<Cottage> optionalCottage = _cottageRepository.findById(id);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(cottage.hasFutureReservationsOrActions())throw new EntityNotDeletableException("Cottage has reservations or actions in future");

    }

    @Override
    @Transactional
    public void unlinkReferencesAndDeleteCottage(Integer id) throws EntityNotFoundException, EntityNotDeletableException {
        checkIfCottageDeletable(id);
        Optional<Cottage> optionalCottage = _cottageRepository.findById(id);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        for(AvailabilityPeriod period:cottage.getAvailabilityPeriods()){
            for(Reservation res:period.getReservations()){
                res.getClient().getReservation().remove(res);
                ((CottageReservation)res).setCottage(null);
                ((CottageReservation)res).getCottageReservationTag().clear();
                res.setClient(null);
                period.getReservations().remove(res);
                res.setAvailabilityPeriod(null);
                //NE RADI, ZASTO?
                //ISTO URADITI ZA AKCIJE, REVIEWOVE KASNIJE
            }
            period.setSaleEntity(null);
            cottage.getAvailabilityPeriods().remove(period);
        }
        for(Picture pic : cottage.getPictures()){
            FileUploadUtil.deleteFile(COTTAGE_PICTURE_DIRECTORY, cottage.getId() + "_" + pic.getName());
            cottage.getPictures().remove(pic);
            pic.setSaleEntity(null);
        }
        cottage.getAdditionalServices().clear();
        cottage.setLocation(null);
        cottage.setCottageOwner(null);
        _cottageRepository.delete(cottage);
    }
    @Override
    public void addAction(String ownerEmail, int cottageId, CottageAction action) throws UnauthorizedAccessException, EntityNotFoundException, EntityNotOwnedException, EntityNotAvailableException {
        CottageOwner owner;
        try{
            owner = (CottageOwner) _userRepository.findByEmail(ownerEmail);
        }catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a cottage owner");
        }
        if(owner == null)throw new UnauthorizedAccessException("Cottage owner not found");
        Optional<Cottage> optionalCottage = _cottageRepository.findById(cottageId);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(cottage.getCottageOwner().getId() != owner.getId())throw new EntityNotOwnedException("Cottage not owned by given user");
        if(!cottage.addAction(action))throw new EntityNotAvailableException("Cottage is not available at the given time");
        _cottageRepository.save(cottage);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setSubject("Cottage reserved");
        message.setText("New action: " + cottage.getName() + " " + action.getRange().getDays() + " days for a price of " + action.getPrice() + ". Available until " + action.getValidUntilAndIncluding());
        for (Client client: cottage.getClient()) {
            message.setTo(client.getEmail());
            _mailSender.send(message);
        }
    }

    @Override
    public void addPicture(int id, MultipartFile picture, String email) throws EntityNotFoundException, EntityNotOwnedException, IOException {
        Optional<Cottage> optionalCottage = _cottageRepository.findById(id);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(!cottage.getCottageOwner().getEmail().equals(email))throw new EntityNotOwnedException("Cottage not owned by given user");
        String pictureFileName = StringUtils.cleanPath(picture.getOriginalFilename());
        Picture pic = new Picture();
        pic.setName(pictureFileName);
        cottage.addPicture(pic);
        _cottageRepository.save(cottage);
        FileUploadUtil.saveFile(COTTAGE_PICTURE_DIRECTORY, cottage.getId() + "_" + pic.getName(), picture);
    }

    @Override
    public void deletePicture(int id, int pic, String email) throws EntityNotOwnedException, EntityNotFoundException {
        Optional<Cottage> optionalCottage = _cottageRepository.findById(id);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(!cottage.getCottageOwner().getEmail().equals(email))throw new EntityNotOwnedException("Cottage not owned by given user");
        for(Picture picture : cottage.getPictures()){
            if(picture.getId() == pic){
                FileUploadUtil.deleteFile(COTTAGE_PICTURE_DIRECTORY, cottage.getId() + "_" + picture.getName());
                return;
            }
        }
        throw new EntityNotFoundException("Picture not found");
    }

    @Override
    public void updateCottage(int id, Cottage updateCottage, String email) throws EntityNotFoundException, EntityNotOwnedException, EntityNotUpdateable {
        Optional<Cottage> optionalCottage = _cottageRepository.findById(id);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(!cottage.getCottageOwner().getEmail().equals(email))throw new EntityNotOwnedException("Cottage not owned by given user");
        if(cottage.hasFutureReservationsOrActions())throw new EntityNotUpdateable("Cottage has reservations or actions in future");
        cottage.setLocation(updateCottage.getLocation());
        cottage.setName(updateCottage.getName());
        cottage.setAdditionalServices(updateCottage.getAdditionalServices());
        cottage.setPricePerDay(updateCottage.getPricePerDay());
        cottage.setRoomNumber(updateCottage.getRoomNumber());
        cottage.setBedNumber(updateCottage.getBedNumber());
        cottage.setDescription(updateCottage.getDescription());
        cottage.setRules(updateCottage.getRules());
        _cottageRepository.save(cottage);
    }

}
