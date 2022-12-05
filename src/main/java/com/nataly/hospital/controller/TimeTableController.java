package com.nataly.hospital.controller;

import com.nataly.hospital.entity.TimeTable;
import com.nataly.hospital.entity.enums.AppointmentStatus;
import com.nataly.hospital.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/time_table")
public class TimeTableController {
    private final TimeTableService timeTableService;

    @GetMapping("/all/{id}")
    public Map<LocalDateTime, AppointmentStatus> getTimeTableByDoctorForWeek(@PathVariable Long id) {
        return timeTableService.getTimeByDoctorForWeek(id);
    }

    @PostMapping("/admin/add")
    public ResponseEntity addTimeTable(@RequestBody TimeTable time) {
        timeTableService.addNewTime(time);
        return ResponseEntity.status(HttpStatus.OK).body("Time was successfully added");
    }
}
