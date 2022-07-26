package code.repository;

import code.model.CottageOwner;
import code.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CottageOwnerRepository extends JpaRepository<CottageOwner, Integer> {
    List<CottageOwner> findByEnabled(boolean enabled);
    CottageOwner findByEmail(String email);
}
