package code.repository;

import code.model.FishingTripQuickReservation;
import code.model.FishingTripReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FishingTripReservationRepository extends JpaRepository<FishingTripReservation, Integer> {
    List<FishingTripReservation> findByFishingTripIdIn(List<Integer> fishingTripIdList);
    List<FishingTripReservation> findByFishingTripId(Integer fishingTripId);
    List<FishingTripReservation> findByClientId(Integer id);
}
