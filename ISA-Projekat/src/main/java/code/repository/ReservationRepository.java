package code.repository;

import code.model.FishingTrip;
import code.model.base.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
