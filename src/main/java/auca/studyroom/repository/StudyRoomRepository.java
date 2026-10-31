package auca.studyroom.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.StudyRoom;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, UUID> {

    Optional<StudyRoom> findByRoomNumber(int roomNumber);

    // C2 — JPQL (NOT EXISTS subquery)
    @Query("SELECT l FROM StudyRoom l WHERE NOT EXISTS " +
            "(SELECT p FROM Supervisor p WHERE p.studyRoom = l) ORDER BY l.roomNumber ASC")
    List<StudyRoom> findFreeStudyRooms();
}
