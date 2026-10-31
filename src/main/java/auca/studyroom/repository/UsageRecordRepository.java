package auca.studyroom.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.RoomBooking;
import auca.studyroom.domain.UsageRecord;

@Repository
public interface UsageRecordRepository extends JpaRepository<UsageRecord, UUID> {

    // E2 — JPQL (path navigation + ORDER BY)
    @Query("SELECT d FROM UsageRecord d WHERE d.roomBooking.student.regNumber = :regNumber " +
            "ORDER BY d.recordedDate DESC")
    List<UsageRecord> findUsageHistoryOfStudent(@Param("regNumber") String regNumber);

    // E3 — DERIVED (existsByRoomBooking)
    boolean existsByRoomBooking(RoomBooking roomBooking);
}