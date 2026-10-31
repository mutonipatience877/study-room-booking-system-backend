package auca.studyroom.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.studyroom.domain.StudyRoom;
import auca.studyroom.service.StudyRoomService;

@RestController
@RequestMapping(value = "/api/study-rooms")
public class StudyRoomController {

    @Autowired
    private StudyRoomService studyRoomServe;

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveStudyRoom(@RequestBody StudyRoom studyRoom) {
        String returnedMessage = studyRoomServe.saveStudyRoom(studyRoom);
        if (returnedMessage.equals("Study room saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<StudyRoom>> getAllStudyRooms() {
        return ResponseEntity.ok(studyRoomServe.getAllStudyRooms());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getStudyRoomById(@PathVariable UUID id) {
        Optional<StudyRoom> studyRoom = studyRoomServe.getStudyRoomById(id);
        if (studyRoom.isEmpty()) {
            return new ResponseEntity<>("The study room with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studyRoom.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateStudyRoom(@PathVariable UUID id, @RequestBody StudyRoom studyRoom) {
        Optional<StudyRoom> updatedStudyRoom = studyRoomServe.updateStudyRoom(id, studyRoom);
        if (updatedStudyRoom.isEmpty()) {
            return new ResponseEntity<>("The study room with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedStudyRoom.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteStudyRoom(@PathVariable UUID id) {
        String returnedMessage = studyRoomServe.deleteStudyRoom(id);
        if (returnedMessage.equals("Study room deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    // C2
    @GetMapping(value = "/free")
    public ResponseEntity<List<StudyRoom>> getFreeStudyRooms() {
        return ResponseEntity.ok(studyRoomServe.getFreeStudyRooms());
    }
}
