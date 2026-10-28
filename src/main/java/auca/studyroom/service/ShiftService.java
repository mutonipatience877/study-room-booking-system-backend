package auca.studyroom.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.studyroom.domain.Shift;
import auca.studyroom.repository.ShiftRepository;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepo;

    public String saveShift(Shift shift) {
        shiftRepo.save(shift);
        return "Shift saved successfully";
    }

    public List<Shift> getAllShifts() {
        return shiftRepo.findAll();
    }

    public Optional<Shift> getShiftById(UUID id) {
        return shiftRepo.findById(id);
    }

    public Optional<Shift> updateShift(UUID id, Shift updatedShift) {
        Optional<Shift> existingShift = shiftRepo.findById(id);
        if (existingShift.isEmpty()) {
            return Optional.empty();
        }
        Shift shiftToUpdate = existingShift.get();
        shiftToUpdate.setName(updatedShift.getName());
        return Optional.of(shiftRepo.save(shiftToUpdate));
    }

    public String deleteShift(UUID id) {
        if (!shiftRepo.existsById(id)) {
            return "The shift with that id does not exist";
        }
        shiftRepo.deleteById(id);
        return "Shift deleted successfully";
    }
}
