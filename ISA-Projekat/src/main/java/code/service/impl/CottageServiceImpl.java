package code.service.impl;

import code.exceptions.entities.*;
import code.exceptions.provider_registration.UnauthorizedAccessException;
import code.exceptions.provider_registration.UserNotFoundException;
import code.model.*;
import code.model.base.*;
import code.model.cottage.Cottage;
import code.model.cottage.CottageAction;
import code.model.cottage.CottageOwner;
import code.model.cottage.CottageReservation;
import code.repository.*;
import code.service.CottageService;
import code.utils.FileUploadUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class CottageServiceImpl implements CottageService {

    private final String COTTAGE_PICTURE_DIRECTORY = "cottage_images";

    private final UserRepository _userRepository;

    private final CottageRepository _cottageRepository;
    private final ReservationRepository _reservationRepository;
    private final PictureRepository _pictureRepository;
    private final ActionRepository _actionRepository;
    private final CurrentSystemTaxPercentageRepository _currentSystemTaxPercentageRepository;
    private final LoyaltyProgramProviderRepository _loyaltyProgramProviderRepository;
    private final LoyaltyProgramClientRepository _loyaltyProgramClientRepository;
    private final CurrentPointsClientGetsAfterReservationRepository _currentPointsClientGetsAfterReservationRepository;
    private final CurrentPointsProviderGetsAfterReservationRepository _currentPointsProviderGetsAfterReservationRepository;
    private final IncomeRecordRepository _incomeRecordRepository;
    private final ClientRepository _clientRepository;
    private final CottageOwnerRepository _cottageOwnerRepository;

    private final JavaMailSender _mailSender;

    public CottageServiceImpl(UserRepository userRepository, CottageRepository cottageRepository, ReservationRepository reservationRepository, PictureRepository pictureRepository, ActionRepository actionRepository, CurrentSystemTaxPercentageRepository currentSystemTaxPercentageRepository, LoyaltyProgramProviderRepository loyaltyProgramProviderRepository, LoyaltyProgramClientRepository loyaltyProgramClientRepository, CurrentPointsClientGetsAfterReservationRepository currentPointsClientGetsAfterReservationRepository, CurrentPointsProviderGetsAfterReservationRepository currentPointsProviderGetsAfterReservationRepository, IncomeRecordRepository incomeRecordRepository, ClientRepository clientRepository, CottageOwnerRepository cottageOwnerRepository, JavaMailSender mailSender){
        _cottageRepository = cottageRepository;
        _userRepository = userRepository;
        _reservationRepository = reservationRepository;
        _pictureRepository = pictureRepository;
        _actionRepository = actionRepository;
        _currentSystemTaxPercentageRepository = currentSystemTaxPercentageRepository;
        _loyaltyProgramProviderRepository = loyaltyProgramProviderRepository;
        _loyaltyProgramClientRepository = loyaltyProgramClientRepository;
        _currentPointsClientGetsAfterReservationRepository = currentPointsClientGetsAfterReservationRepository;
        _currentPointsProviderGetsAfterReservationRepository = currentPointsProviderGetsAfterReservationRepository;
        _incomeRecordRepository = incomeRecordRepository;
        _clientRepository = clientRepository;
        _cottageOwnerRepository = cottageOwnerRepository;
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
    @Transactional()
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
    @Transactional()
    public void addReservation(String clientEmail, int cottageId, CottageReservation reservation, String email) throws EntityNotFoundException, UserNotFoundException, InvalidReservationException, EntityNotOwnedException, EntityNotAvailableException, UnauthorizedAccessException, ClientCancelledThisPeriodException {
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
        reservation.setSystemCharge(_currentSystemTaxPercentageRepository.findById(1).get().getCurrentSystemTaxPercentage());
        if(!cottage.addReservation(reservation))throw new EntityNotAvailableException("Cottage is not available at the given time");
        _cottageRepository.save(cottage);
        makeIncomeRecord(reservation, owner);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setTo(client.getEmail());
        message.setSubject("Cottage reserved");
        message.setText("Cottage " + cottage.getName() + " has been successfully reserved" );
        _mailSender.send(message);
    }

    private void makeIncomeRecord(Reservation reservation, CottageOwner owner) {
        IncomeRecord incRec = new IncomeRecord();
        incRec.setReservationStart(reservation.getDateRange().getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        incRec.setReservationEnd(reservation.getDateRange().getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        incRec.setReserved(true);
        incRec.setDateOfEntry(LocalDate.now());
        incRec.setReservationProvider(owner);
        incRec.setReservationPrice(reservation.getPrice());
        incRec.setSystemTaxPercentage(reservation.getSystemCharge());
        incRec.setPercentageProviderKeepsIfReservationCancelled(reservation.getReservationRefund());
        incRec.setSystemIncome(incRec.getReservationPrice() * incRec.getSystemTaxPercentage()/100);
        incRec.setProviderIncome(incRec.getReservationPrice() - incRec.getSystemIncome());
        _incomeRecordRepository.save(incRec);
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
        for(Picture pic : cottage.getPictures()){
            FileUploadUtil.deleteFile(COTTAGE_PICTURE_DIRECTORY, cottage.getId() + "_" + pic.getName());
        }
        _cottageRepository.delete(cottage);
    }
    @Override
    @Transactional()
    public void addAction(String ownerEmail, int cottageId, CottageAction action) throws UnauthorizedAccessException, EntityNotFoundException, EntityNotOwnedException, EntityNotAvailableException, InvalidReservationException {
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
        action.setSystemCharge(_currentSystemTaxPercentageRepository.findById(1).get().getCurrentSystemTaxPercentage());
        if(!cottage.addAction(action))throw new EntityNotAvailableException("Cottage is not available at the given time");
        _cottageRepository.save(cottage);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko76589@gmail.com");
        message.setSubject("Cottage reserved");
        message.setText("New action: " + cottage.getName() + " " + action.getRange().durationInDays() + " days for a price of " + action.getPrice() + ". Available until " + action.getValidUntilAndIncluding());
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
                cottage.getPictures().remove(picture);
                _cottageRepository.save(cottage);
                return;
            }
        }
        throw new EntityNotFoundException("Picture not found");
    }

    @Override
    @Transactional()
    public void updateCottage(int id, Cottage updateCottage, String email) throws EntityNotFoundException, EntityNotOwnedException, EntityNotUpdateableException {
        Optional<Cottage> optionalCottage = _cottageRepository.findById(id);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(!cottage.getCottageOwner().getEmail().equals(email))throw new EntityNotOwnedException("Cottage not owned by given user");
        if(cottage.hasFutureReservationsOrActions())throw new EntityNotUpdateableException("Cottage has reservations or actions in future");
        cottage.setLocation(updateCottage.getLocation());
        cottage.setName(updateCottage.getName());
        cottage.setAdditionalServices(updateCottage.getAdditionalServices());
        cottage.setPricePerDay(updateCottage.getPricePerDay());
        cottage.setRoomNumber(updateCottage.getRoomNumber());
        cottage.setBedNumber(updateCottage.getBedNumber());
        cottage.setDescription(updateCottage.getDescription());
        cottage.setRules(updateCottage.getRules());
        cottage.setReservationRefund(updateCottage.getReservationRefund());
        _cottageRepository.save(cottage);
    }

    @Override
    public void addReservationCommentary(int id, int resId, String email, OwnerCommentary commentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented {
        Optional<Cottage> optionalCottage = _cottageRepository.findById(id);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(!cottage.getCottageOwner().getEmail().equals(email))throw new EntityNotOwnedException("Cottage not owned by given user");
        Optional<Reservation> optRes = _reservationRepository.findById(resId);
        if(!optRes.isPresent())throw new EntityNotFoundException("Reservation not found");
        CottageReservation res;
        try{
            res = (CottageReservation) optRes.get();
        }catch(ClassCastException ex){
            throw new EntityNotFoundException("Reservation is not a cottage reservation");
        }
        if(res.getOwnerCommentary() != null) throw new ReservationOrActionAlreadyCommented("You have already commented on this reservation");
        if(res.getCottage().getId().intValue() != cottage.getId().intValue())throw new EntityNotFoundException("Reservation not found in given cottage");
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
    public void addActionCommentary(int id, int actId, String email, OwnerCommentary commentary) throws EntityNotFoundException, EntityNotOwnedException, ReservationOrActionNotFinishedException, ReservationOrActionAlreadyCommented {
        Optional<Cottage> optionalCottage = _cottageRepository.findById(id);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        if(!cottage.getCottageOwner().getEmail().equals(email))throw new EntityNotOwnedException("Cottage not owned by given user");
        Optional<Action> optAct = _actionRepository.findById(actId);
        if(!optAct.isPresent())throw new EntityNotFoundException("Action not found");
        CottageAction act;
        try{
            act = (CottageAction) optAct.get();
        }catch(ClassCastException ex){
            throw new EntityNotFoundException("Action is not a cottage action");
        }
        if(act.getOwnerCommentary() != null) throw new ReservationOrActionAlreadyCommented("You have already commented on this action");
        if(act.getCottage().getId().intValue() != cottage.getId().intValue())throw new EntityNotFoundException("Action not found in given cottage");
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
    @Scheduled(cron="0 0 1 * * *")
    @Transactional
    public void awardLoyaltyPoints() {
        List<Cottage> cottageList = _cottageRepository.findAll();
        List<LoyaltyProgramProvider> loyaltyProgramProviders = _loyaltyProgramProviderRepository.findAll();
        List<LoyaltyProgramClient> loyaltyProgramClients = _loyaltyProgramClientRepository.findAll();
        Collections.sort(loyaltyProgramClients, Comparator.comparing(LoyaltyProgramClient::getPointsNeeded));
        Collections.sort(loyaltyProgramProviders, Comparator.comparing(LoyaltyProgramProvider::getPointsNeeded));
        for(Cottage cottage : cottageList){
            for(AvailabilityPeriod period : cottage.getAvailabilityPeriods()){
                for(Reservation res : period.getReservations()){
                    if(res.getDateRange().DateRangeInPast() && !res.isLoyaltyPointsGiven() && res.getReservationStatus() != ReservationStatus.cancelled){
                        res.getClient().setLoyaltyPoints(res.getClient().getLoyaltyPoints() + _currentPointsClientGetsAfterReservationRepository.findById(1).get().getCurrentPointsClientGetsAfterReservation());
                        for (LoyaltyProgramClient lpc : loyaltyProgramClients) {
                            if (res.getClient().getLoyaltyPoints() >= lpc.getPointsNeeded()) {
                                res.getClient().setCategory(lpc);
                            }
                        }
                        _clientRepository.save(res.getClient());
                        cottage.getCottageOwner().setLoyaltyPoints(cottage.getCottageOwner().getLoyaltyPoints() + _currentPointsProviderGetsAfterReservationRepository.findById(1).get().getCurrentPointsProviderGetsAfterReservation());
                        for (LoyaltyProgramProvider lpp : loyaltyProgramProviders) {
                            if (cottage.getCottageOwner().getLoyaltyPoints() >= lpp.getPointsNeeded()) {
                                cottage.getCottageOwner().setCategory(lpp);
                            }
                        }
                        _cottageOwnerRepository.save(cottage.getCottageOwner());
                        res.setLoyaltyPointsGiven(true);
                        _reservationRepository.save(res);
                    }
                }
                for(Action act : period.getActions()){
                    if(act.getRange().DateRangeInPast() && !act.isLoyaltyPointsGiven() && act.getClient() != null){
                        act.getClient().setLoyaltyPoints(act.getClient().getLoyaltyPoints() + _currentPointsClientGetsAfterReservationRepository.findById(1).
                                get().getCurrentPointsClientGetsAfterReservation());
                        for (LoyaltyProgramClient lpc : loyaltyProgramClients) {
                            if (act.getClient().getLoyaltyPoints() >= lpc.getPointsNeeded()) {
                                act.getClient().setCategory(lpc);
                            }
                        }
                        _clientRepository.save(act.getClient());
                        cottage.getCottageOwner().setLoyaltyPoints(cottage.getCottageOwner().getLoyaltyPoints() + _currentPointsProviderGetsAfterReservationRepository.findById(1).get().getCurrentPointsProviderGetsAfterReservation());
                        for (LoyaltyProgramProvider lpp : loyaltyProgramProviders) {
                            if (cottage.getCottageOwner().getLoyaltyPoints() >= lpp.getPointsNeeded()) {
                                cottage.getCottageOwner().setCategory(lpp);
                            }
                        }
                        _cottageOwnerRepository.save(cottage.getCottageOwner());
                        act.setLoyaltyPointsGiven(true);
                        _actionRepository.save(act);
                    }
                }
            }
        }
    }
    @Override
    public List<Cottage> getAllCottages(){
        return _cottageRepository.findAll();
    }
    @Override
    public Cottage getCottage(Integer id) throws EntityNotFoundException {
        Cottage cottage = _cottageRepository.findById(id).orElse(null);
        if(cottage == null) {
            throw new EntityNotFoundException("Cottage doesn't exist!");
        }
        return cottage;
    }

    @Override
    public List<PictureBase64> getCottageImagesAsBase64(int id) throws EntityNotFoundException, IOException {
        Cottage cottage = _cottageRepository.findById(id).orElse(null);
        if(cottage == null) {
            throw new EntityNotFoundException("Cottage doesn't exist!");
        }
        List<PictureBase64> pictures = new ArrayList<>();
        for(Picture pic:cottage.getPictures()){
            pictures.add(new PictureBase64(FileUploadUtil.convertToBase64(COTTAGE_PICTURE_DIRECTORY, cottage.getId() + "_" + pic.getName()), pic.getId()));
        }
        return pictures;
    }

    @Override
    public CottageReservation getCottageReservation(int cottageId, int resId) throws EntityNotFoundException {
        Optional<Cottage> optionalCottage = _cottageRepository.findById(cottageId);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        for(AvailabilityPeriod period:cottage.getAvailabilityPeriods()){
            for(Reservation res:period.getReservations()){
                if(res.getId() == resId)return (CottageReservation) res;
            }
        }
        throw new EntityNotFoundException("Reservation not found");
    }

    @Override
    public CottageAction getCottageAction(Integer id, Integer actId) throws EntityNotFoundException {
        Optional<Cottage> optionalCottage = _cottageRepository.findById(id);
        if(!optionalCottage.isPresent())throw new EntityNotFoundException("Cottage not found");
        Cottage cottage = optionalCottage.get();
        for(AvailabilityPeriod period:cottage.getAvailabilityPeriods()){
            for(Action act:period.getActions()){
                if(act.getId() == actId)return (CottageAction) act;
            }
        }
        throw new EntityNotFoundException("Action not found");
    }
}
