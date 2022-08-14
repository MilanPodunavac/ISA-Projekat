package code.repository;

import code.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CottageRepository extends JpaRepository<Cottage, Integer> {
    @Query("select c.id from Cottage c where c.cottageOwner.id = ?1")
    List<Integer> findByCottageOwner(Integer id);
}
