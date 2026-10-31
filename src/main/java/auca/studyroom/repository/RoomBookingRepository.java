package auca.studyroom.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.BookingStatus;
import auca.studyroom.domain.RoomBooking;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, UUID> {

    // B2 — DERIVED (enum parameter + OrderBy)
    List<RoomBooking> findByStatusOrderByBookingDateAsc(BookingStatus status);

    // B3 — DERIVED (Between, both dates inclusive)
    List<RoomBooking> findByBookingDateBetweenOrderByBookingDateAsc(LocalDate start, LocalDate end);

    // D1 — DERIVED (existsBy + And + StatusNot)
    boolean existsBySupervisorIdAndBookingDateAndStatusNot(UUID supervisorId, LocalDate bookingDate, BookingStatus status);

    // F1 — JPQL (GROUP BY + COUNT)
    @Query("SELECT r.status, COUNT(r) FROM RoomBooking r GROUP BY r.status")
    List<Object[]> countRoomBookingsByStatus();

    // F2 — JPQL (UPDATE + @Modifying). @Transactional goes on the service method.
    @Modifying
    @Query("UPDATE RoomBooking r SET r.status = auca.studyroom.domain.BookingStatus.CANCELLED " +
            "WHERE r.supervisor.id = :supervisorId AND r.bookingDate = :date " +
            "AND r.status <> auca.studyroom.domain.BookingStatus.USED")
    int cancelSupervisorDay(@Param("supervisorId") UUID supervisorId, @Param("date") LocalDate date);
}