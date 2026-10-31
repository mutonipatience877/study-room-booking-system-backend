package auca.studyroom.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, UUID> {

    // C3 — JPQL (IS EMPTY, no counting)
    @Query("SELECT s FROM Shift s WHERE s.supervisors IS EMPTY")
    List<Shift> findUnusedShifts();
}
