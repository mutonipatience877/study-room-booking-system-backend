package auca.studyroom.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.studyroom.domain.BookingStatus;
import auca.studyroom.domain.RoomBooking;
import auca.studyroom.service.RoomBookingService;

@RestController
@RequestMapping(value = "/api/room-bookings")
public class RoomBookingController {

    @Autowired
    private RoomBookingService roomBookingServe;

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveRoomBooking(@RequestBody RoomBooking roomBooking,
                                                  @RequestParam UUID studentId,
                                                  @RequestParam UUID supervisorId) {
        String returnedMessage = roomBookingServe.saveRoomBooking(roomBooking, studentId, supervisorId);
        if (returnedMessage.equals("Room booking saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        }
        if (returnedMessage.equals("Room booking date cannot be in the past")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        }
        if (returnedMessage.equals("Supervisor already has a booking on that date")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<RoomBooking>> getAllRoomBookings() {
        return ResponseEntity.ok(roomBookingServe.getAllRoomBookings());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRoomBookingById(@PathVariable UUID id) {
        Optional<RoomBooking> roomBooking = roomBookingServe.getRoomBookingById(id);
        if (roomBooking.isEmpty()) {
            return new ResponseEntity<>("The room booking with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roomBooking.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateRoomBooking(@PathVariable UUID id, @RequestBody RoomBooking roomBooking) {
        Optional<RoomBooking> updatedRoomBooking = roomBookingServe.updateRoomBooking(id, roomBooking);
        if (updatedRoomBooking.isEmpty()) {
            return new ResponseEntity<>("The room booking with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedRoomBooking.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteRoomBooking(@PathVariable UUID id) {
        String returnedMessage = roomBookingServe.deleteRoomBooking(id);
        if (returnedMessage.equals("Room booking deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }
        if (returnedMessage.equals("That room booking has usage records, delete them first")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    // B2
    @GetMapping(value = "/by-status")
    public ResponseEntity<List<RoomBooking>> getRoomBookingsByStatus(@RequestParam BookingStatus status) {
        return ResponseEntity.ok(roomBookingServe.getRoomBookingsByStatus(status));
    }

    // B3
    @GetMapping(value = "/between")
    public ResponseEntity<List<RoomBooking>> getRoomBookingsBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(roomBookingServe.getRoomBookingsBetween(startDate, endDate));
    }

    // D2
    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<String> updateRoomBookingStatus(@PathVariable UUID id, @RequestParam BookingStatus status) {
        String returnedMessage = roomBookingServe.updateRoomBookingStatus(id, status);
        if (returnedMessage.equals("Room booking status updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }
        if (returnedMessage.equals("The room booking with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
    }

    // F1
    @GetMapping(value = "/stats/by-status")
    public ResponseEntity<List<Object[]>> getRoomBookingStatsByStatus() {
        return ResponseEntity.ok(roomBookingServe.getRoomBookingStatsByStatus());
    }

    // F2
    @PatchMapping(value = "/cancel-day")
    public ResponseEntity<String> cancelSupervisorDay(@RequestParam UUID supervisorId, @RequestParam String date) {
        LocalDate day = LocalDate.parse(date);
        return ResponseEntity.ok(roomBookingServe.cancelSupervisorDay(supervisorId, day));
    }
    // Bonus 2: page by page
    @GetMapping(value = "/page")
    public ResponseEntity<org.springframework.data.domain.Page<RoomBooking>> getRoomBookingsPage(@RequestParam int page,
                                                                                                 @RequestParam int size,
                                                                                                 @RequestParam String sort) {
        String[] sortParts = sort.split(",");
        org.springframework.data.domain.Sort.Direction direction = sortParts.length > 1
                ? org.springframework.data.domain.Sort.Direction.fromString(sortParts[1])
                : org.springframework.data.domain.Sort.Direction.ASC;
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(
                page, size, org.springframework.data.domain.Sort.by(direction, sortParts[0]));
        return ResponseEntity.ok(roomBookingServe.getRoomBookingsPage(pageable));
    }
}