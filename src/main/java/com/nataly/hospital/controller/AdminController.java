package com.nataly.hospital.controller;

import com.nataly.hospital.dto.DoctorDto;
import com.nataly.hospital.dto.PatientDto;
import com.nataly.hospital.dto.ResultDto;
import com.nataly.hospital.entity.Contract;
import com.nataly.hospital.entity.User;
import com.nataly.hospital.entity.enums.Role;
import com.nataly.hospital.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final ContractService contractService;
    private final ResultService resultService;
    private final RecordService recordService;
    private final NotificationService notificationService;

    @GetMapping("/contract/list")
    public List<Contract> getAllContract(){
        return contractService.getAllContract();
    }

    @GetMapping("/contract/number")
    public Contract getContractByDateAndNumber(@RequestParam(value = "date") String date,
                                               @RequestParam(value = "number") int number){
        return contractService.getContractByDateAndNumber(date, number);
    }

    @PostMapping("/contract/add")
    public ResponseEntity addContract (@RequestBody PatientDto patient,
                                       @RequestParam(value = "priceList") List<String> priceList){
        contractService.addContract(patient,priceList);
        return ResponseEntity.status(HttpStatus.OK).body("Contract was successfully added");
    }

//    добавить результат по id пациента
    @PostMapping("/result/add/{id}")
    public ResponseEntity addResult(@RequestBody ResultDto resultDto, @PathVariable long id) {
        resultService.addResult(resultDto, id);
        return ResponseEntity.status(HttpStatus.OK).body("Result was successfully added");
    }

    @PostMapping("/record/add")
    public ResponseEntity addRecord(@RequestBody DoctorDto doctorDto,
                                         @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                         @RequestParam(name = "phoneNumber")String phoneNumber){
        User patient = userService.getUserByPhoneNumber(phoneNumber);
        recordService.addNewRecordPatient(patient, doctorDto, date);
        notificationService.sendMail(patient.getEmail(), "Запись на прием", "Вы успешно записаны на прием");
        return ResponseEntity.status(HttpStatus.OK).body("Record was successfully added");
    }

    @GetMapping("/users/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/phone")
    public User getUserByPhoneNumber(@RequestParam(name = "phoneNumber") String phoneNumber){
        return userService.getUserByPhoneNumber(phoneNumber);
    }

    @GetMapping("/users/type")
    public List<User> getAllByUserRole(@RequestParam(name = "role") String role){
        return userService.getAllByUserRole(Role.valueOf(role));
    }

    @PostMapping("/patient/add")
    public User addPatient(@RequestBody PatientDto patientDto){
        return userService.addPatient(patientDto);
    }

    @PostMapping("/doctor/add")
    public User addDoctor(@RequestBody DoctorDto doctorDto){
        return userService.addDoctor(doctorDto);
    }
}
