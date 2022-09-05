package code.repository;

import code.model.ReviewFishingTrip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewFishingTripRepository extends JpaRepository<ReviewFishingTrip, Integer> {
    List<ReviewFishingTrip> findByFishingTripIdAndApproved(Integer id, Boolean approved);
    List<ReviewFishingTrip> findByApproved(Boolean approved);
}