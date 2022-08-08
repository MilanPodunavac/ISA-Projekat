package code.repository;

import code.model.FishingTrip;
import code.model.FishingTripPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FishingTripPictureRepository extends JpaRepository<FishingTripPicture, Integer> {
    @Query("select ftp from FishingTripPicture ftp where ftp.fishingTrip.id = ?1")
    List<FishingTripPicture> findByFishingTrip(Integer id);
}