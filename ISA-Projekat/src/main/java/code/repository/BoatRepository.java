package code.repository;

import code.model.boat.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoatRepository extends JpaRepository<Boat, Integer> {
    @Query("select b.id from Boat b where b.boatOwner.id = ?1")
    List<Integer> findByBoatOwner(Integer id);
}
