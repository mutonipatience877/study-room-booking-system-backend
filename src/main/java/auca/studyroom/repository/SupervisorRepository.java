package auca.studyroom.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.Supervisor;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, UUID> {

    // C1 — JPQL (JOIN on the shifts collection + LOWER)
    @Query("SELECT DISTINCT p FROM Supervisor p JOIN p.shifts c " +
            "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Supervisor> findSupervisorsByShiftName(@Param("name") String name);
}
