package auca.studyroom.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.studyroom.domain.Shift;
import auca.studyroom.service.ShiftService;

@RestController
@RequestMapping(value = "/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftServe;

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveShift(@RequestBody Shift shift) {
        String returnedMessage = shiftServe.saveShift(shift);
        if (returnedMessage.equals("Shift saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Shift>> getAllShifts() {
        return ResponseEntity.ok(shiftServe.getAllShifts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getShiftById(@PathVariable UUID id) {
        Optional<Shift> shift = shiftServe.getShiftById(id);
        if (shift.isEmpty()) {
            return new ResponseEntity<>("The shift with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shift.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateShift(@PathVariable UUID id, @RequestBody Shift shift) {
        Optional<Shift> updatedShift = shiftServe.updateShift(id, shift);
        if (updatedShift.isEmpty()) {
            return new ResponseEntity<>("The shift with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedShift.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteShift(@PathVariable UUID id) {
        String returnedMessage = shiftServe.deleteShift(id);
        if (returnedMessage.equals("Shift deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    // C3
    @GetMapping(value = "/unused")
    public ResponseEntity<List<Shift>> getUnusedShifts() {
        return ResponseEntity.ok(shiftServe.getUnusedShifts());
    }
}
