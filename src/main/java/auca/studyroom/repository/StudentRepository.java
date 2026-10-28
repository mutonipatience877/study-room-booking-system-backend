package auca.studyroom.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    boolean existsByRegNumber(String regNumber);

    // B1 — DERIVED (IgnoreCase + OrderBy)
    List<Student> findByFacultyIgnoreCaseOrderByLastNameAsc(String faculty);
}