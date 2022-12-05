package com.nataly.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataly.hospital.dto.ResultDto;
import com.nataly.hospital.entity.Record;
import com.nataly.hospital.entity.*;
import com.nataly.hospital.entity.enums.DoctorPosition;
import com.nataly.hospital.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
public class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private UtilTest utilTest;

    @BeforeEach
    public void clean() {
        userRepository.deleteAll();
        accountRepository.deleteAll();
        resultRepository.deleteAll();
    }

    @Test
    public void getAllResultByPatientIdTest() throws Exception {
        User patient = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 05, 14),
                "8-904-528-84-45", "asd@ma.ru");
        Result result = utilTest.createTestResult(LocalDate.now(), "result description", patient);
        ResultDto resultDto = new ResultDto(LocalDate.of(2022, 12, 5), "result description");
        User doctor = utilTest.createTestDoctor("Александр", "Крылов", LocalDate.of(1988, 7, 22),
                "8-912-234-34-34", "qwer@ma.ru", DoctorPosition.THERAPIST, "doctor description");
        Account doctorAccount = utilTest.createTestAccount("8-912-234-34-34", "doctor", doctor);

        this.mockMvc.perform(
                        get("/doctor/result/{patientId}", patient.getId()).with(user(doctorAccount))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(resultDto))));
    }

    @Test
    public void addNewVisitTest() throws Exception {
        User patient = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 5, 14),
                "8-904-528-84-45", "asd@ma.ru");
        User doctor = utilTest.createTestDoctor("Александр", "Крылов", LocalDate.of(1988, 7, 22),
                "8-912-234-34-34", "qwer@ma.ru", DoctorPosition.THERAPIST, "doctor description");
        Account doctorAccount = utilTest.createTestAccount("8-912-234-34-34", "doctor", doctor);
        TimeTable timeTable = utilTest.createTestTimeTable(LocalDateTime.of(2022, 12, 5, 12, 30),
                32, doctor);
        Record record = recordRepository.save(new Record(1L, timeTable, patient));
        this.mockMvc.perform(
                        post("/doctor/visit/add").with(user(doctorAccount))
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("recordId", String.valueOf(record.getId()))
                                .param("description", "description")
                                .param("recipe", "recipe"))
                .andExpect(status().isOk());
        Assertions.assertEquals("description", visitRepository.findAll().get(0).getDescription());
    }
}
