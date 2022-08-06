package code.repository;

import code.model.FishingTrip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishingTripRepository extends JpaRepository<FishingTrip, Integer> {
}
