package code.repository;

import code.model.Review;
import code.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByApproved(Boolean approved);
}