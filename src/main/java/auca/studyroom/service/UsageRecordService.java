package auca.studyroom.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.studyroom.domain.BookingStatus;
import auca.studyroom.domain.RoomBooking;
import auca.studyroom.domain.UsageRecord;
import auca.studyroom.repository.RoomBookingRepository;
import auca.studyroom.repository.UsageRecordRepository;

@Service
public class UsageRecordService {

    @Autowired
    private UsageRecordRepository usageRecordRepo;
    @Autowired
    private RoomBookingRepository roomBookingRepo;

    // E1
    public String saveUsageRecord(UsageRecord usageRecord, UUID roomBookingId) {
        Optional<RoomBooking> getRoomBooking = roomBookingRepo.findById(roomBookingId);
        if (getRoomBooking.isEmpty()) {
            return "The room booking with that id does not exist";
        }
        if (getRoomBooking.get().getStatus() != BookingStatus.USED) {
            return "Usage records are only for USED room bookings";
        }
        usageRecord.setRecordedDate(LocalDate.now());
        usageRecord.setRoomBooking(getRoomBooking.get());
        usageRecordRepo.save(usageRecord);
        return "Usage record saved successfully";
    }

    // helper to see what is in the table (not graded)
    public List<UsageRecord> getAllUsageRecords() {
        return usageRecordRepo.findAll();
    }

    // E2
    public List<UsageRecord> getUsageHistoryOfStudent(String regNumber) {
        return usageRecordRepo.findUsageHistoryOfStudent(regNumber);
    }

    // E3
    public String deleteUsageRecord(UUID id) {
        if (!usageRecordRepo.existsById(id)) {
            return "The usage record with that id does not exist";
        }
        usageRecordRepo.deleteById(id);
        return "Usage record deleted successfully";
    }
}