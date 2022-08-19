package code.repository;

import code.model.AccountDeletionRequest;
import code.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDeletionRequestRepository extends JpaRepository<AccountDeletionRequest, Integer> {
    AccountDeletionRequest findByUserId(Integer id);
}
