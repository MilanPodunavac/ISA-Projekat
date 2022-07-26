package code.repository;

import code.model.CottageOwner;
import code.model.FishingInstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FishingInstructorRepository extends JpaRepository<FishingInstructor, Integer> {
    List<FishingInstructor> findByEnabled(boolean enabled);
    FishingInstructor findByEmail(String email);
}
