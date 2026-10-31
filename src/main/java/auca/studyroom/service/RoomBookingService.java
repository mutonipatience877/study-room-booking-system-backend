package auca.studyroom.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auca.studyroom.domain.BookingStatus;
import auca.studyroom.domain.RoomBooking;
import auca.studyroom.domain.Student;
import auca.studyroom.domain.Supervisor;
import auca.studyroom.repository.RoomBookingRepository;
import auca.studyroom.repository.StudentRepository;
import auca.studyroom.repository.SupervisorRepository;
import auca.studyroom.repository.UsageRecordRepository;

@Service
public class RoomBookingService {

    @Autowired
    private RoomBookingRepository roomBookingRepo;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private SupervisorRepository supervisorRepo;
    @Autowired
    private UsageRecordRepository usageRecordRepo;

    public String saveRoomBooking(RoomBooking roomBooking, UUID studentId, UUID supervisorId) {
        Optional<Student> getStudent = studentRepo.findById(studentId);
        if (getStudent.isEmpty()) {
            return "The student with that id does not exist";
        }
        Optional<Supervisor> getSupervisor = supervisorRepo.findById(supervisorId);
        if (getSupervisor.isEmpty()) {
            return "The supervisor with that id does not exist";
        }
        // D1 rule 1: no booking in the past
        if (roomBooking.getBookingDate().isBefore(LocalDate.now())) {
            return "Room booking date cannot be in the past";
        }
        // D1 rule 2: the supervisor cannot have two active bookings on the same date
        boolean alreadyBooked = roomBookingRepo.existsBySupervisorIdAndBookingDateAndStatusNot(
                supervisorId, roomBooking.getBookingDate(), BookingStatus.CANCELLED);
        if (alreadyBooked) {
            return "Supervisor already has a booking on that date";
        }
        // D1 rule 3: a new booking always starts as REQUESTED
        roomBooking.setStatus(BookingStatus.REQUESTED);
        roomBooking.setStudent(getStudent.get());
        roomBooking.setSupervisor(getSupervisor.get());
        roomBookingRepo.save(roomBooking);
        return "Room booking saved successfully";
    }

    public List<RoomBooking> getAllRoomBookings() {
        return roomBookingRepo.findAll();
    }

    public Optional<RoomBooking> getRoomBookingById(UUID id) {
        return roomBookingRepo.findById(id);
    }

    public Optional<RoomBooking> updateRoomBooking(UUID id, RoomBooking updatedRoomBooking) {
        Optional<RoomBooking> existingRoomBooking = roomBookingRepo.findById(id);
        if (existingRoomBooking.isEmpty()) {
            return Optional.empty();
        }
        RoomBooking roomBookingToUpdate = existingRoomBooking.get();
        roomBookingToUpdate.setBookingDate(updatedRoomBooking.getBookingDate());
        roomBookingToUpdate.setStartHour(updatedRoomBooking.getStartHour());
        roomBookingToUpdate.setPurpose(updatedRoomBooking.getPurpose());
        return Optional.of(roomBookingRepo.save(roomBookingToUpdate));
    }

    public String deleteRoomBooking(UUID id) {
        Optional<RoomBooking> getRoomBooking = roomBookingRepo.findById(id);
        if (getRoomBooking.isEmpty()) {
            return "The room booking with that id does not exist";
        }
        // E3: protect a booking that already has usage records
        if (usageRecordRepo.existsByRoomBooking(getRoomBooking.get())) {
            return "That room booking has usage records, delete them first";
        }
        roomBookingRepo.deleteById(id);
        return "Room booking deleted successfully";
    }

    // B2
    public List<RoomBooking> getRoomBookingsByStatus(BookingStatus status) {
        return roomBookingRepo.findByStatusOrderByBookingDateAsc(status);
    }

    // B3
    public List<RoomBooking> getRoomBookingsBetween(LocalDate start, LocalDate end) {
        return roomBookingRepo.findByBookingDateBetweenOrderByBookingDateAsc(start, end);
    }

    // D2 — the allowed arrows of the status flow
    private boolean canChangeStatus(BookingStatus from, BookingStatus to) {
        if (from == BookingStatus.REQUESTED) {
            return to == BookingStatus.APPROVED || to == BookingStatus.CANCELLED;
        }
        if (from == BookingStatus.APPROVED) {
            return to == BookingStatus.USED || to == BookingStatus.CANCELLED;
        }
        return false; // USED and CANCELLED are final
    }

    // D2
    public String updateRoomBookingStatus(UUID id, BookingStatus newStatus) {
        Optional<RoomBooking> getRoomBooking = roomBookingRepo.findById(id);
        if (getRoomBooking.isEmpty()) {
            return "The room booking with that id does not exist";
        }
        RoomBooking roomBooking = getRoomBooking.get();
        if (!canChangeStatus(roomBooking.getStatus(), newStatus)) {
            return "Cannot change status from " + roomBooking.getStatus() + " to " + newStatus;
        }
        roomBooking.setStatus(newStatus);
        roomBookingRepo.save(roomBooking);
        return "Room booking status updated successfully";
    }

    // F1
    public List<Object[]> getRoomBookingStatsByStatus() {
        return roomBookingRepo.countRoomBookingsByStatus();
    }

    // F2 — @Transactional is required because the query changes data
    @Transactional
    public String cancelSupervisorDay(UUID supervisorId, LocalDate date) {
        int cancelled = roomBookingRepo.cancelSupervisorDay(supervisorId, date);
        return cancelled + " room bookings cancelled";
    }
}