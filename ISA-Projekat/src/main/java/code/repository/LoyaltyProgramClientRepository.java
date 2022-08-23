package code.repository;

import code.model.LoyaltyProgramClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyProgramClientRepository extends JpaRepository<LoyaltyProgramClient, Integer> {
}
