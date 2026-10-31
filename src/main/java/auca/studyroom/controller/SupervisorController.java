package auca.studyroom.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.studyroom.domain.Supervisor;
import auca.studyroom.service.SupervisorService;

@RestController
@RequestMapping(value = "/api/supervisors")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorServe;

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveSupervisor(@RequestBody Supervisor supervisor,
                                                 @RequestParam(required = false) Integer roomNumber) {
        String returnedMessage = supervisorServe.saveSupervisor(supervisor, roomNumber);
        if (returnedMessage.equals("Supervisor saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Supervisor>> getAllSupervisors() {
        return ResponseEntity.ok(supervisorServe.getAllSupervisors());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSupervisorById(@PathVariable UUID id) {
        Optional<Supervisor> supervisor = supervisorServe.getSupervisorById(id);
        if (supervisor.isEmpty()) {
            return new ResponseEntity<>("The supervisor with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(supervisor.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateSupervisor(@PathVariable UUID id, @RequestBody Supervisor supervisor) {
        Optional<Supervisor> updatedSupervisor = supervisorServe.updateSupervisor(id, supervisor);
        if (updatedSupervisor.isEmpty()) {
            return new ResponseEntity<>("The supervisor with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedSupervisor.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteSupervisor(@PathVariable UUID id) {
        String returnedMessage = supervisorServe.deleteSupervisor(id);
        if (returnedMessage.equals("Supervisor deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    // D3 — built early because the seed data needs supervisors with shifts
    @PutMapping(value = "/{supervisorId}/shifts/{shiftId}")
    public ResponseEntity<String> addShiftToSupervisor(@PathVariable UUID supervisorId, @PathVariable UUID shiftId) {
        String returnedMessage = supervisorServe.addShiftToSupervisor(supervisorId, shiftId);
        if (returnedMessage.equals("Shift added to the supervisor")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }
        if (returnedMessage.equals("The supervisor already has that shift")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    // C1
    @GetMapping(value = "/by-shift")
    public ResponseEntity<List<Supervisor>> getSupervisorsByShift(@RequestParam String name) {
        return ResponseEntity.ok(supervisorServe.getSupervisorsByShift(name));
    }
}
