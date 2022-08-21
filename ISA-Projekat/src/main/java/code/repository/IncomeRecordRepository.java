package code.repository;

import code.model.IncomeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRecordRepository extends JpaRepository<IncomeRecord, Integer> {
    List<IncomeRecord> findByReservationOwnerId(Integer id);
}
