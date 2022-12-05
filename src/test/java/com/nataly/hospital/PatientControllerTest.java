package com.nataly.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataly.hospital.entity.Account;
import com.nataly.hospital.entity.Contract;
import com.nataly.hospital.entity.PriceList;
import com.nataly.hospital.entity.User;
import com.nataly.hospital.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
public class PatientControllerTest {
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
    private TimeTableRepository timeTableRepository;
    @Autowired
    private UtilTest utilTest;

    @BeforeEach
    public void clean() {
        userRepository.deleteAll();
        accountRepository.deleteAll();
        recordRepository.deleteAll();
        visitRepository.deleteAll();
        timeTableRepository.deleteAll();
    }

    @Test
    public void getAllMyContractsTest() throws Exception {
        PriceList service1 = utilTest.createTestPriceList("service1", 1000.00);
        PriceList service2 = utilTest.createTestPriceList("service2", 1500.00);
        PriceList service3 = utilTest.createTestPriceList("service3", 900.00);
        PriceList service4 = utilTest.createTestPriceList("service4", 1100.00);
        List<PriceList> priceList1 = List.of(service1, service2);
        List<PriceList> priceList2 = List.of(service3, service4);
        User patient = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 5, 14),
                "8-904-528-84-45", "asd@ma.ru");
        Account patientAccount = utilTest.createTestAccount("8-904-528-84-45", "patient", patient);
        Contract contract11 = utilTest.createTestContract(LocalDate.of(2022, 11, 14), 11, priceList1, patient);
        Contract contract12 = utilTest.createTestContract(LocalDate.of(2022, 12, 2), 12, priceList2, patient);
        this.mockMvc.perform(
                        get("/patient/contracts").with(user(patientAccount))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(contract11, contract12))));
    }

}
