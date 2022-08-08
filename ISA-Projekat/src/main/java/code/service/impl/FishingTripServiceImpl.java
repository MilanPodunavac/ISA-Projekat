package code.service.impl;

import code.exceptions.fishing_trip.EditAnotherInstructorFishingTripException;
import code.exceptions.fishing_trip.FishingTripNotFoundException;
import code.model.*;
import code.repository.FishingTripPictureRepository;
import code.repository.FishingTripRepository;
import code.service.FishingTripService;
import code.service.UserService;
import code.utils.FileUploadUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FishingTripServiceImpl implements FishingTripService {
    private final FishingTripRepository _fishingTripRepository;
    private final FishingTripPictureRepository _fishingTripPictureRepository;
    private final UserService _userService;

    public FishingTripServiceImpl(FishingTripPictureRepository fishingTripPictureRepository, FishingTripRepository fishingTripRepository, UserService userService) {
        this._fishingTripPictureRepository = fishingTripPictureRepository;
        this._fishingTripRepository = fishingTripRepository;
        this._userService = userService;
    }

    @Override
    public FishingTrip save(FishingTrip fishingTrip) {
        setInstructor(fishingTrip);
        return _fishingTripRepository.save(fishingTrip);
    }

    @Transactional
    @Override
    public FishingTrip edit(FishingTrip fishingTrip) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException {
        throwExceptionIfFishingTripNotFound(fishingTrip.getId());
        throwExceptionIfEditAnotherInstructorFishingTrip(fishingTrip.getId());
        setInstructor(fishingTrip);
        setLocationId(fishingTrip);
        fishingTrip.setPictures(new HashSet<>(_fishingTripPictureRepository.findByFishingTrip(fishingTrip.getId())));
        return _fishingTripRepository.save(fishingTrip);
    }

    private void throwExceptionIfFishingTripNotFound(Integer fishingTripId) throws FishingTripNotFoundException {
        Optional<FishingTrip> fishingTrip = _fishingTripRepository.findById(fishingTripId);
        if (!fishingTrip.isPresent()) {
            throw new FishingTripNotFoundException("Fishing trip not found!");
        }
    }

    private void throwExceptionIfEditAnotherInstructorFishingTrip(Integer fishingTripId) throws EditAnotherInstructorFishingTripException {
        FishingTrip ft = _fishingTripRepository.getById(fishingTripId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        FishingInstructor fishingInstructor = (FishingInstructor) _userService.findById(user.getId());
        if (fishingInstructor.getId() != ft.getFishingInstructor().getId()) {
            throw new EditAnotherInstructorFishingTripException("You can't edit another instructor's fishing trip!");
        }
    }

    private void setInstructor(FishingTrip fishingTrip) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        FishingInstructor fishingInstructor = (FishingInstructor) _userService.findById(user.getId());
        fishingTrip.setFishingInstructor(fishingInstructor);
    }

    private void setLocationId(FishingTrip fishingTrip) {
        FishingTrip ft = _fishingTripRepository.getById(fishingTrip.getId());
        fishingTrip.getLocation().setId(ft.getLocation().getId());
    }

    @Transactional
    @Override
    public void editPictures(Integer id, MultipartFile[] pictures) throws FishingTripNotFoundException, EditAnotherInstructorFishingTripException, IOException {
        throwExceptionIfFishingTripNotFound(id);
        throwExceptionIfEditAnotherInstructorFishingTrip(id);

        String fishingTripPicturesDirectory = "fishing_trip_pictures";
        FishingTrip fishingTrip = _fishingTripRepository.getById(id);
        deletePreviousPictures(fishingTripPicturesDirectory, fishingTrip);
        FishingTrip fishingTripFromDatabase = saveNewPicturesToDatabase(pictures, fishingTrip);
        saveNewPicturesToDirectory(fishingTripFromDatabase, pictures, fishingTripPicturesDirectory);
    }

    private void deletePreviousPictures(String fishingTripPicturesDirectory, FishingTrip fishingTrip) {
        List<FishingTripPicture> fishingTripPictures = _fishingTripPictureRepository.findByFishingTrip(fishingTrip.getId());
        for (FishingTripPicture fishingTripPicture : fishingTripPictures) {
            fishingTrip.getPictures().remove(fishingTripPicture);
            fishingTripPicture.setFishingTrip(null);
            FileUploadUtil.deleteFile(fishingTripPicturesDirectory, fishingTripPicture.getId() + "_" + fishingTripPicture.getName());
        }
    }

    private FishingTrip saveNewPicturesToDatabase(MultipartFile[] pictures, FishingTrip fishingTrip) {
        if (pictures != null) {
            for (MultipartFile picture : pictures) {
                String pictureFileName = StringUtils.cleanPath(picture.getOriginalFilename());

                FishingTripPicture fishingTripPicture = new FishingTripPicture();
                fishingTripPicture.setName(pictureFileName);
                fishingTripPicture.setFishingTrip(fishingTrip);
                fishingTrip.getPictures().add(fishingTripPicture);
            }
        }

        return _fishingTripRepository.save(fishingTrip);
    }

    private void saveNewPicturesToDirectory(FishingTrip fishingTripFromDatabase, MultipartFile[] pictures, String fishingTripPicturesDirectory) throws IOException {
        int i = 0;
        for (FishingTripPicture fishingTripPicture : fishingTripFromDatabase.getPictures()) {
            MultipartFile picture = pictures[i++];
            FileUploadUtil.saveFile(fishingTripPicturesDirectory, fishingTripPicture.getId() + "_" + fishingTripPicture.getName(), picture);
        }
    }
}
