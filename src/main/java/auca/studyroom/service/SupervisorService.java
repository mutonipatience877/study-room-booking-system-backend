package auca.studyroom.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.studyroom.domain.Shift;
import auca.studyroom.domain.StudyRoom;
import auca.studyroom.domain.Supervisor;
import auca.studyroom.repository.ShiftRepository;
import auca.studyroom.repository.StudyRoomRepository;
import auca.studyroom.repository.SupervisorRepository;

@Service
public class SupervisorService {

    @Autowired
    private SupervisorRepository supervisorRepo;
    @Autowired
    private StudyRoomRepository studyRoomRepo;
    @Autowired
    private ShiftRepository shiftRepo;

    public String saveSupervisor(Supervisor supervisor, Integer roomNumber) {
        if (roomNumber != null) {
            Optional<StudyRoom> getStudyRoom = studyRoomRepo.findByRoomNumber(roomNumber);
            if (getStudyRoom.isEmpty()) {
                return "The study room with that roomNumber does not exist";
            }
            supervisor.setStudyRoom(getStudyRoom.get());
        }
        supervisorRepo.save(supervisor);
        return "Supervisor saved successfully";
    }

    public List<Supervisor> getAllSupervisors() {
        return supervisorRepo.findAll();
    }

    public Optional<Supervisor> getSupervisorById(UUID id) {
        return supervisorRepo.findById(id);
    }

    public Optional<Supervisor> updateSupervisor(UUID id, Supervisor updatedSupervisor) {
        Optional<Supervisor> existingSupervisor = supervisorRepo.findById(id);
        if (existingSupervisor.isEmpty()) {
            return Optional.empty();
        }
        Supervisor supervisorToUpdate = existingSupervisor.get();
        supervisorToUpdate.setFirstName(updatedSupervisor.getFirstName());
        supervisorToUpdate.setLastName(updatedSupervisor.getLastName());
        supervisorToUpdate.setEmail(updatedSupervisor.getEmail());
        return Optional.of(supervisorRepo.save(supervisorToUpdate));
    }

    public String deleteSupervisor(UUID id) {
        if (!supervisorRepo.existsById(id)) {
            return "The supervisor with that id does not exist";
        }
        supervisorRepo.deleteById(id);
        return "Supervisor deleted successfully";
    }

    // D3 — built early because the seed data needs supervisors with shifts
    public String addShiftToSupervisor(UUID supervisorId, UUID shiftId) {
        Optional<Supervisor> getSupervisor = supervisorRepo.findById(supervisorId);
        if (getSupervisor.isEmpty()) {
            return "The supervisor with that id does not exist";
        }
        Optional<Shift> getShift = shiftRepo.findById(shiftId);
        if (getShift.isEmpty()) {
            return "That shift does not exist";
        }
        Supervisor supervisor = getSupervisor.get();
        if (supervisor.getShifts().contains(getShift.get())) {
            return "The supervisor already has that shift";
        }
        supervisor.getShifts().add(getShift.get());
        supervisorRepo.save(supervisor);
        return "Shift added to the supervisor";
    }

    // C1
    public List<Supervisor> getSupervisorsByShift(String name) {
        return supervisorRepo.findSupervisorsByShiftName(name);
    }
}
