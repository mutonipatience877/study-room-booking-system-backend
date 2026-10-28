package auca.studyroom.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.studyroom.domain.BookingStatus;
import auca.studyroom.domain.RoomBooking;
import auca.studyroom.domain.Student;
import auca.studyroom.domain.Supervisor;
import auca.studyroom.repository.RoomBookingRepository;
import auca.studyroom.repository.StudentRepository;
import auca.studyroom.repository.SupervisorRepository;

@Service
public class RoomBookingService {

    @Autowired
    private RoomBookingRepository roomBookingRepo;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private SupervisorRepository supervisorRepo;

    public String saveRoomBooking(RoomBooking roomBooking, UUID studentId, UUID supervisorId) {
        Optional<Student> getStudent = studentRepo.findById(studentId);
        if (getStudent.isEmpty()) {
            return "The student with that id does not exist";
        }
        Optional<Supervisor> getSupervisor = supervisorRepo.findById(supervisorId);
        if (getSupervisor.isEmpty()) {
            return "The supervisor with that id does not exist";
        }
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
        roomBookingToUpdate.setStatus(updatedRoomBooking.getStatus());
        return Optional.of(roomBookingRepo.save(roomBookingToUpdate));
    }

    public String deleteRoomBooking(UUID id) {
        if (!roomBookingRepo.existsById(id)) {
            return "The room booking with that id does not exist";
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
}