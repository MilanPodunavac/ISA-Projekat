package code.repository;

import code.model.Complaint;
import code.model.ComplaintFishingInstructor;
import code.model.FishingTrip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintFishingInstructorRepository extends JpaRepository<ComplaintFishingInstructor, Integer> {
    List<ComplaintFishingInstructor> findByFishingInstructorId(Integer id);
}