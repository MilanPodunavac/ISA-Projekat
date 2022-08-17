package code.repository;

import code.model.base.AvailabilityPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityPeriodRepository extends JpaRepository<AvailabilityPeriod, Integer> {
}
