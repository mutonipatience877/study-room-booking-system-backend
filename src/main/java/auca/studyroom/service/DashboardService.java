package auca.studyroom.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.studyroom.domain.BookingStatus;
import auca.studyroom.repository.RoomBookingRepository;
import auca.studyroom.repository.StudentRepository;
import auca.studyroom.repository.StudyRoomRepository;
import auca.studyroom.repository.SupervisorRepository;
import auca.studyroom.repository.UsageRecordRepository;

@Service
public class DashboardService {

    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private SupervisorRepository supervisorRepo;
    @Autowired
    private StudyRoomRepository studyRoomRepo;
    @Autowired
    private RoomBookingRepository roomBookingRepo;
    @Autowired
    private UsageRecordRepository usageRecordRepo;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("students", studentRepo.count());
        summary.put("supervisors", supervisorRepo.count());
        summary.put("freeStudyRooms", (long) studyRoomRepo.findFreeStudyRooms().size());
        summary.put("bookingsToday", roomBookingRepo.countByBookingDate(LocalDate.now()));
        summary.put("requested", roomBookingRepo.countByStatus(BookingStatus.REQUESTED));
        summary.put("usageRecords", usageRecordRepo.count());
        return summary;
    }
}
