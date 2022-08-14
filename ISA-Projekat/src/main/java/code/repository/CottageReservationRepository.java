package code.repository;

import code.model.CottageReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CottageReservationRepository extends JpaRepository<CottageReservation, Integer> {
    List<CottageReservation> findByCottageIdIn(List<Integer> cottageIdList);
    List<CottageReservation> findByClientId(Integer id);
}
