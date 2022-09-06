package code.repository;

import code.model.AccountDeletionRequest;
import code.model.Review;
import code.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDeletionRequestRepository extends JpaRepository<AccountDeletionRequest, Integer> {
    List<AccountDeletionRequest> findByUserId(Integer id);
    List<AccountDeletionRequest> findByProcessed(Boolean processed);
}
