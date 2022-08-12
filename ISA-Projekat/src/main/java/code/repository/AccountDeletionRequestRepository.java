package code.repository;

import code.model.AccountDeletionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDeletionRequestRepository extends JpaRepository<AccountDeletionRequest, Integer> {
}
