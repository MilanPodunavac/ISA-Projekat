package code.repository;

import code.model.Complaint;
import code.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    List<Complaint> findByResponded(Boolean responded);
}