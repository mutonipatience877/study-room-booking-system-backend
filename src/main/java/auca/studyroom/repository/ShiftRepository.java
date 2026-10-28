package auca.studyroom.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, UUID> {

}
