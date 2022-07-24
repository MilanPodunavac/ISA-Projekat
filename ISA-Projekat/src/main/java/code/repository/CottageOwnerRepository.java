package code.repository;

import code.model.CottageOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageOwnerRepository extends JpaRepository<CottageOwner, Integer> {
}
