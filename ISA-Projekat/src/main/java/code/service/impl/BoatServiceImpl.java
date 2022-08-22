package code.service.impl;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.Client;
import code.model.IncomeRecord;
import code.model.base.*;
import code.model.boat.Boat;
import code.model.boat.BoatAction;
import code.model.boat.BoatOwner;
import code.model.boat.BoatReservation;
import code.model.cottage.CottageOwner;
import code.repository.*;
import code.service.BoatService;
import code.utils.FileUploadUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class BoatServiceImpl  implements BoatService {

    private final String BOAT_PICTURE_DIRECTORY = "boat_images";
    private final BoatRepository _boatRepository;
    private final UserRepository _userRepository;
    private final ReservationRepository _reservationRepository;
    private final ActionRepository _actionRepository;
    private final CurrentSystemTaxPercentageRepository _currentSystemTaxPercentageRepository;
    private final IncomeRecordRepository _incomeRecordRepository;
    private final JavaMailSender _mailSender;

    public BoatServiceImpl(BoatRepository boatRepository, UserRepository userRepository, ReservationRepository reservationRepository, ActionRepository actionRepository, CurrentSystemTaxPercentageRepository currentSystemTaxPercentageRepository, IncomeRecordRepository incomeRecordRepository, JavaMailSender mailSender){
        _boatRepository = boatRepository;
        _userRepository = userRepository;
        _reservationRepository = reservationRepository;
        _actionRepository = actionRepository;
        _currentSystemTaxPercentageRepository = currentSystemTaxPercentageRepository;
        _incomeRecordRepository = incomeRecordRepository;
        _mailSender = mailSender;
    }

    @Override
    public void throwExceptionIfBoatDontExist(Integer id) throws EntityNotFoundException {
        Optional<Boat> boat = _boatRepository.findById(id);
        if (!boat.isPresent()) {
            throw new EntityNotFoundException("Boat doesn't exist!");
        }
    }

    @Override
    public void checkIfBoatDeletable(Integer id) throws EntityNotDeletableException, EntityNotFoundException {
        Optional<Boat> optionalBoat = _boatRepository.findById(id);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Cottage not found");
        Boat boat = optionalBoat.get();
        if(boat.hasFutureReservationsOrActions())throw new EntityNotDeletableException("Cottage has reservations or actions in future");
    }

    @Override
    @Transactional
    public void unlinkReferencesAndDeleteBoat(Integer id) throws EntityNotFoundException, EntityNotDeletableException {
        checkIfBoatDeletable(id);
        Optional<Boat> optionalBoat = _boatRepository.findById(id);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Cottage not found");
        Boat boat = optionalBoat.get();
        for(Picture pic : boat.getPictures()){
            FileUploadUtil.deleteFile(BOAT_PICTURE_DIRECTORY, boat.getId() + "_" + pic.getName());
        }
        _boatRepository.delete(boat);
    }

    @Override
    public void addBoat(Boat boat) throws UnauthorizedAccessException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        owner.addBoat(boat);
        _boatRepository.save(boat);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void addAvailabilityPeriod(int boatId, AvailabilityPeriod period) throws AvailabilityPeriodBadRangeException, UserNotFoundException, EntityNotFoundException, EntityNotOwnedException, UnauthorizedAccessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        Optional<Boat> optionalBoat = _boatRepository.findById(boatId);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Boat not found");
        Boat boat = optionalBoat.get();
        if(boat.getBoatOwner().getId() != owner.getId()) throw new EntityNotOwnedException("Boat not owned by given user");
        boat.addAvailabilityPeriod(period);
        _boatRepository.save(boat);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void addReservation(String clientEmail, int boatId, BoatReservation reservation) throws EntityNotFoundException, UserNotFoundException, InvalidReservationException, EntityNotOwnedException, EntityNotAvailableException, UnauthorizedAccessException, ClientCancelledThisPeriodException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        Optional<Boat> optionalBoat = _boatRepository.findById(boatId);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Boat not found");
        Boat boat = optionalBoat.get();
        if(boat.getBoatOwner().getId() != owner.getId()) throw new EntityNotOwnedException("Boat not owned by given user");
        Client client;
        try{
            client = (Client) _userRepository.findByEmail(clientEmail);
        }catch(ClassCastException ex){
            throw new UserNotFoundException("User is not a client");
        }
        if(client == null)throw new UserNotFoundException("Client not found");
        reservation.setClient(client);
        reservation.setSystemCharge(_currentSystemTaxPercentageRepository.findById(1).get().getCurrentSystemTaxPercentage());
        if(!boat.addReservation(reservation))throw new EntityNotAvailableException("Boat is not available at the given time");
        _boatRepository.save(boat);
        makeIncomeRecord(reservation, owner);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(client.getEmail());
        message.setSubject("Boat reserved");
        message.setText("Boat " + boat.getName() + " has been successfully reserved" );
        _mailSender.send(message);
    }

    private void makeIncomeRecord(Reservation reservation, BoatOwner owner) {
        IncomeRecord incRec = new IncomeRecord();
        incRec.setReservationStart(reservation.getDateRange().getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        incRec.setReservationEnd(reservation.getDateRange().getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        incRec.setReserved(true);
        incRec.setDateOfEntry(LocalDate.now());
        incRec.setReservationOwner(owner);
        incRec.setReservationPrice(reservation.getPrice());
        incRec.setSystemTaxPercentage(reservation.getSystemCharge());
        incRec.setPercentageOwnerKeepsIfReservationCancelled(reservation.getReservationRefund());
        incRec.setSystemIncome(incRec.getReservationPrice() * incRec.getSystemTaxPercentage()/100);
        incRec.setOwnerIncome(incRec.getReservationPrice() - incRec.getSystemIncome());
        _incomeRecordRepository.save(incRec);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void addAction(int boatId, BoatAction action) throws UnauthorizedAccessException, EntityNotFoundException, EntityNotOwnedException, EntityNotAvailableException, InvalidReservationException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        Optional<Boat> optionalBoat = _boatRepository.findById(boatId);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Boat not found");
        Boat boat = optionalBoat.get();
        if(boat.getBoatOwner().getId() != owner.getId())throw new EntityNotOwnedException("Boat not owned by given user");
        action.setSystemCharge(_currentSystemTaxPercentageRepository.findById(1).get().getCurrentSystemTaxPercentage());
        if(!boat.addAction(action))throw new EntityNotAvailableException("Boat is not available at the given time");
        _boatRepository.save(boat);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setSubject("Cottage reserved");
        message.setText("New action: " + boat.getName() + " " + action.getRange().getDays() + " days for a price of " + action.getPrice() + ". Available until " + action.getValidUntilAndIncluding());
        for (Client client: boat.getClient()) {
            message.setTo(client.getEmail());
            _mailSender.send(message);
        }
    }

    @Override
    public void addPicture(int id, MultipartFile picture) throws EntityNotFoundException, EntityNotOwnedException, IOException, UnauthorizedAccessException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        Optional<Boat> optionalBoat = _boatRepository.findById(id);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Boat not found");
        Boat boat = optionalBoat.get();
        if(boat.getBoatOwner().getId() != owner.getId())throw new EntityNotOwnedException("Boat not owned by given user");
        String pictureFileName = StringUtils.cleanPath(picture.getOriginalFilename());
        Picture pic = new Picture();
        pic.setName(pictureFileName);
        boat.addPicture(pic);
        _boatRepository.save(boat);
        FileUploadUtil.saveFile(BOAT_PICTURE_DIRECTORY, boat.getId() + "_" + pic.getName(), picture);
    }

    @Override
    public void deletePicture(int id, int pic) throws EntityNotOwnedException, EntityNotFoundException, UnauthorizedAccessException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        Optional<Boat> optionalBoat = _boatRepository.findById(id);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Boat not found");
        Boat boat = optionalBoat.get();
        if(boat.getBoatOwner().getId() != owner.getId())throw new EntityNotOwnedException("Boat not owned by given user");
        for(Picture picture : boat.getPictures()){
            if(picture.getId() == pic){
                FileUploadUtil.deleteFile(BOAT_PICTURE_DIRECTORY, boat.getId() + "_" + picture.getName());
                boat.getPictures().remove(picture);
                _boatRepository.save(boat);
                return;
            }
        }
        throw new EntityNotFoundException("Picture not found");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void updateBoat(int id, Boat updateBoat) throws EntityNotFoundException, EntityNotOwnedException, EntityNotUpdateableException, UnauthorizedAccessException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        Optional<Boat> optionalBoat = _boatRepository.findById(id);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Boat not found");
        Boat boat = optionalBoat.get();
        if(boat.getBoatOwner().getId() != owner.getId())throw new EntityNotOwnedException("Boat not owned by given user");
        if(boat.hasFutureReservationsOrActions())throw new EntityNotUpdateableException("Boat has reservations or actions in future");
        boat.setLocation(updateBoat.getLocation());
        boat.setName(updateBoat.getName());
        boat.setAdditionalServices(updateBoat.getAdditionalServices());
        boat.setEngineNumber(updateBoat.getEngineNumber());
        boat.setEnginePower(updateBoat.getEnginePower());
        boat.setFishingEquipment(updateBoat.getFishingEquipment());
        boat.setLength(updateBoat.getLength());
        boat.setMaxPeople(updateBoat.getMaxPeople());
        boat.setNavigationalEquipment(updateBoat.getNavigationalEquipment());
        boat.setReservationRefund(updateBoat.getReservationRefund());
        boat.setType(updateBoat.getType());
        boat.setDescription(updateBoat.getDescription());
        boat.setMaxSpeed(updateBoat.getMaxSpeed());
        boat.setPricePerDay(updateBoat.getPricePerDay());
        boat.setRules(updateBoat.getRules());
        _boatRepository.save(boat);
    }

    @Override
    public void addReservationCommentary(int id, int resId, OwnerCommentary commentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented, UnauthorizedAccessException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        Optional<Boat> optionalBoat = _boatRepository.findById(id);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Boat not found");
        Boat boat = optionalBoat.get();
        if(boat.getBoatOwner().getId() != owner.getId())throw new EntityNotOwnedException("Boat not owned by given user");
        Optional<Reservation> optRes = _reservationRepository.findById(resId);
        if(!optRes.isPresent())throw new EntityNotFoundException("Reservation not found");
        BoatReservation res;
        try{
            res = (BoatReservation) optRes.get();
        }catch(ClassCastException ex){
            throw new EntityNotFoundException("Reservation is not a boat reservation");
        }
        if(res.getOwnerCommentary() != null) throw new ReservationOrActionAlreadyCommented("You have already commented on this reservation");
        if(res.getBoat().getId().intValue() != boat.getId().intValue())throw new EntityNotFoundException("Reservation not found in given boat");
        if(res.getDateRange().getEndDate().getTime() > System.currentTimeMillis()) throw new ReservationOrActionNotFinishedException("Reservation is not finished");
        commentary.setPenaltyGiven(!commentary.isClientCame());
        commentary.setAdminApproved(false);
        res.setOwnerCommentary(commentary);
        if(!commentary.isClientCame()){
            commentary.setSanctionSuggested(false);
            res.getClient().setPenaltyPoints(res.getClient().getPenaltyPoints() + 1);
            if(res.getClient().getPenaltyPoints() >= 3)res.getClient().setBanned(true);
            _userRepository.save(res.getClient());
        }
        _reservationRepository.save(res);
    }

    @Override
    public void addActionCommentary(int id, int actId, OwnerCommentary commentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented, UnauthorizedAccessException, UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BoatOwner owner;
        try{
            owner = (BoatOwner) auth.getPrincipal();
        }
        catch(ClassCastException ex){
            throw new UnauthorizedAccessException("User is not a boat owner");
        }
        if(owner == null) throw new UserNotFoundException("Boat owner not found");
        Optional<Boat> optionalBoat = _boatRepository.findById(id);
        if(!optionalBoat.isPresent())throw new EntityNotFoundException("Boat not found");
        Boat boat = optionalBoat.get();
        if(boat.getBoatOwner().getId() != owner.getId())throw new EntityNotOwnedException("Boat not owned by given user");
        Optional<Action> optAct = _actionRepository.findById(actId);
        if(!optAct.isPresent())throw new EntityNotFoundException("Reservation not found");
        BoatAction act;
        try{
            act = (BoatAction) optAct.get();
        }catch(ClassCastException ex){
            throw new EntityNotFoundException("Action is not a boat actin");
        }
        if(act.getOwnerCommentary() != null) throw new ReservationOrActionAlreadyCommented("You have already commented on this action");
        if(act.getBoat().getId().intValue() != boat.getId().intValue())throw new EntityNotFoundException("Reservation not found in given boat");
        if(act.getRange().getEndDate().getTime() > System.currentTimeMillis()) throw new ReservationOrActionNotFinishedException("Action is not finished");
        if(act.getClient() == null)throw new ReservationOrActionNotFinishedException("This action was not activated");
        commentary.setPenaltyGiven(!commentary.isClientCame());
        commentary.setAdminApproved(false);
        act.setOwnerCommentary(commentary);
        if(!commentary.isClientCame()){
            commentary.setSanctionSuggested(false);
            act.getClient().setPenaltyPoints(act.getClient().getPenaltyPoints() + 1);
            if(act.getClient().getPenaltyPoints() >= 3)act.getClient().setBanned(true);
            _userRepository.save(act.getClient());
        }
        _actionRepository.save(act);
    }
}
