package code.repository;

import code.model.FishingInstructorAvailablePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FishingInstructorAvailablePeriodRepository extends JpaRepository<FishingInstructorAvailablePeriod, Integer> {
    @Query("select fiap from FishingInstructorAvailablePeriod fiap where fiap.fishingInstructor.id = ?1")
    List<FishingInstructorAvailablePeriod> findByFishingInstructor(Integer id);
}
