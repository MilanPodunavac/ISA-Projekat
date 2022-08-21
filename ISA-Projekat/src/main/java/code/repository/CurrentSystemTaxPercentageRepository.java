package code.repository;

import code.model.CurrentSystemTaxPercentage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentSystemTaxPercentageRepository extends JpaRepository<CurrentSystemTaxPercentage, Integer> {
}
