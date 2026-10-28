package auca.studyroom.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.StudyRoom;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, UUID> {

    Optional<StudyRoom> findByRoomNumber(int roomNumber);
}
