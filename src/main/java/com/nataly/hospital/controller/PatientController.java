package com.nataly.hospital.controller;

import com.nataly.hospital.dto.DoctorDto;
import com.nataly.hospital.dto.PatientDto;
import com.nataly.hospital.dto.ResultDto;
import com.nataly.hospital.entity.Account;
import com.nataly.hospital.entity.Contract;
import com.nataly.hospital.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final UserService userService;
    private final ContractService contractService;
    private final ResultService resultService;
    private final VisitService visitService;
    private final RecordService recordService;
    private final NotificationService notificationService;

    @PostMapping("/edit")
    public ResponseEntity editMe(@AuthenticationPrincipal Account account,
                                 @RequestBody PatientDto patient) {
        userService.editPatient(account, patient);
        return ResponseEntity.status(HttpStatus.OK).body("Patient successfully edit");
    }

    @GetMapping("/contracts")
    public List<Contract> getAllMyContracts(@AuthenticationPrincipal Account account) {
        return contractService.getContractByPatient(account.getUser().getId());
    }

    @GetMapping("/result")
    public List<ResultDto> getAllMyResult(@AuthenticationPrincipal Account account) {
        return resultService.getAllResultByPatientId(account.getUser().getId());
    }

    @GetMapping("/records")
    public List<LocalDateTime> getAllRecord(@AuthenticationPrincipal Account account) {
        return recordService.getAllRecordsByPatient(account.getUser());
    }

    @PostMapping("/record/add")
    public ResponseEntity addRecordPatient(@AuthenticationPrincipal Account account,
                                           @RequestBody DoctorDto doctorDto,
                                           @RequestParam(name = "date") LocalDateTime date) {
        recordService.addNewRecordPatient(account.getUser(), doctorDto, date);
        notificationService.sendMail(account.getUser().getEmail(), "Запись на прием", "Вы успешно записаны на прием");
        return ResponseEntity.status(HttpStatus.OK).body("Record was successfully added");
    }
}
