package code.repository;

import code.dto.fishing_trip.FishingInstructorFishingTripTableGetDto;
import code.model.FishingTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FishingTripRepository extends JpaRepository<FishingTrip, Integer> {
    @Query("select ft.id from FishingTrip ft where ft.fishingInstructor.id = ?1")
    List<Integer> findByFishingInstructor(Integer id);
    List<FishingTrip> findByFishingInstructorId(Integer id);

}
