package auca.studyroom.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.studyroom.domain.RoomBooking;
import auca.studyroom.service.RoomBookingService;
import java.time.LocalDate;
import auca.studyroom.domain.BookingStatus;

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
}
