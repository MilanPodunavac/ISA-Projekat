package code.repository;

import code.model.FishingTripQuickReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FishingTripQuickReservationRepository extends JpaRepository<FishingTripQuickReservation, Integer> {
    List<FishingTripQuickReservation> findByFishingTripIdIn(List<Integer> fishingTripIdList);
}
