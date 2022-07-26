package code.repository;

import code.model.Boat;
import code.model.BoatOwner;
import code.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoatOwnerRepository extends JpaRepository<BoatOwner, Integer> {
    List<BoatOwner> findByEnabled(boolean enabled);
    BoatOwner findByEmail(String email);
}
