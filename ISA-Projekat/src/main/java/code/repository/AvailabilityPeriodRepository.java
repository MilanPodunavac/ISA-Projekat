package code.repository;

import code.model.AvailabilityPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityPeriodRepository extends JpaRepository<AvailabilityPeriod, Integer> {
}
