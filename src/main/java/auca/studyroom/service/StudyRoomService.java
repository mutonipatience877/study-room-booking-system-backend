package auca.studyroom.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.studyroom.domain.StudyRoom;
import auca.studyroom.repository.StudyRoomRepository;

@Service
public class StudyRoomService {

    @Autowired
    private StudyRoomRepository studyRoomRepo;

    public String saveStudyRoom(StudyRoom studyRoom) {
        studyRoomRepo.save(studyRoom);
        return "Study room saved successfully";
    }

    public List<StudyRoom> getAllStudyRooms() {
        return studyRoomRepo.findAll();
    }

    public Optional<StudyRoom> getStudyRoomById(UUID id) {
        return studyRoomRepo.findById(id);
    }

    public Optional<StudyRoom> updateStudyRoom(UUID id, StudyRoom updatedStudyRoom) {
        Optional<StudyRoom> existingStudyRoom = studyRoomRepo.findById(id);
        if (existingStudyRoom.isEmpty()) {
            return Optional.empty();
        }
        StudyRoom studyRoomToUpdate = existingStudyRoom.get();
        studyRoomToUpdate.setRoomNumber(updatedStudyRoom.getRoomNumber());
        studyRoomToUpdate.setName(updatedStudyRoom.getName());
        return Optional.of(studyRoomRepo.save(studyRoomToUpdate));
    }

    public String deleteStudyRoom(UUID id) {
        if (!studyRoomRepo.existsById(id)) {
            return "The study room with that id does not exist";
        }
        studyRoomRepo.deleteById(id);
        return "Study room deleted successfully";
    }
}
