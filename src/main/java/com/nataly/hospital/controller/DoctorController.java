package com.nataly.hospital.controller;

import com.nataly.hospital.dto.ResultDto;
import com.nataly.hospital.service.ResultService;
import com.nataly.hospital.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {
    private final ResultService resultService;
    private final VisitService visitService;

    @GetMapping("/result/{patientId}")
    public List<ResultDto> getAllResultByPatientId(@PathVariable long patientId) {
        return resultService.getAllResultByPatientId(patientId);
    }

    @PostMapping("/visit/add")
    public ResponseEntity addNewVisit(@RequestParam(name = "recordId") long recordId,
                                      @RequestParam(name = "description") String description,
                                      @RequestParam(name = "recipe") String recipe) {
        visitService.addVisit(recordId, description, recipe);
        return ResponseEntity.status(HttpStatus.OK).body("Visit was successfully added");
    }
}
