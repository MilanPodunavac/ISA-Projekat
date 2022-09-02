package code.repository;

import code.model.boat.BoatReservation;
import code.model.cottage.CottageReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoatReservationRepository extends JpaRepository<BoatReservation, Integer> {
    List<BoatReservation> findByBoatIdIn(List<Integer> boatIdList);
    List<BoatReservation> findByClientId(Integer id);
}
