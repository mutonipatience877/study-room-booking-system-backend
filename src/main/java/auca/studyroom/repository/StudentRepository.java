package auca.studyroom.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    boolean existsByRegNumber(String regNumber);

    // B1 — DERIVED (IgnoreCase + OrderBy)
    List<Student> findByFacultyIgnoreCaseOrderByLastNameAsc(String faculty);

    // C4 — JPQL (DISTINCT + path navigation)
    @Query("SELECT DISTINCT r.student FROM RoomBooking r WHERE r.supervisor.id = :supervisorId")
    List<Student> findStudentsOfSupervisor(@Param("supervisorId") UUID supervisorId);
}