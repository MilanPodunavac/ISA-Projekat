package code.repository;

import code.model.FishingInstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishingInstructorRepository extends JpaRepository<FishingInstructor, Integer> {
}
