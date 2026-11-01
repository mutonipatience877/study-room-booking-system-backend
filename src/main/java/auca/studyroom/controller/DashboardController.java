package auca.studyroom.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.studyroom.service.DashboardService;

@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardServe;

    @GetMapping(value = "/summary")
    public ResponseEntity<Map<String, Long>> getSummary() {
        return ResponseEntity.ok(dashboardServe.getSummary());
    }
}