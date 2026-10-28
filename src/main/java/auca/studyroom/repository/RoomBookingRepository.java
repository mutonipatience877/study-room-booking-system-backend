package auca.studyroom.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.studyroom.domain.BookingStatus;
import auca.studyroom.domain.RoomBooking;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, UUID> {

    // B2 — DERIVED (enum parameter + OrderBy)
    List<RoomBooking> findByStatusOrderByBookingDateAsc(BookingStatus status);

    // B3 — DERIVED (Between, both dates inclusive)
    List<RoomBooking> findByBookingDateBetweenOrderByBookingDateAsc(LocalDate start, LocalDate end);
}