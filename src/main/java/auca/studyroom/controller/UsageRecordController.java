package auca.studyroom.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.studyroom.domain.UsageRecord;
import auca.studyroom.service.UsageRecordService;

@RestController
@RequestMapping(value = "/api/usage-records")
public class UsageRecordController {

    @Autowired
    private UsageRecordService usageRecordServe;

    // E1
    @PostMapping(value = "/save")
    public ResponseEntity<String> saveUsageRecord(@RequestBody UsageRecord usageRecord,
                                                  @RequestParam UUID roomBookingId) {
        String returnedMessage = usageRecordServe.saveUsageRecord(usageRecord, roomBookingId);
        if (returnedMessage.equals("Usage record saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        }
        if (returnedMessage.equals("Usage records are only for USED room bookings")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    // helper (not graded)
    @GetMapping(value = "/all")
    public ResponseEntity<List<UsageRecord>> getAllUsageRecords() {
        return ResponseEntity.ok(usageRecordServe.getAllUsageRecords());
    }

    // E2
    @GetMapping(value = "/student/{regNumber}")
    public ResponseEntity<List<UsageRecord>> getUsageHistoryOfStudent(@PathVariable String regNumber) {
        return ResponseEntity.ok(usageRecordServe.getUsageHistoryOfStudent(regNumber));
    }

    // E3
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteUsageRecord(@PathVariable UUID id) {
        String returnedMessage = usageRecordServe.deleteUsageRecord(id);
        if (returnedMessage.equals("Usage record deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }
        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }
}