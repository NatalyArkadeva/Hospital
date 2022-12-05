package com.nataly.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataly.hospital.dto.DoctorDto;
import com.nataly.hospital.dto.PatientDto;
import com.nataly.hospital.dto.ResultDto;
import com.nataly.hospital.entity.Account;
import com.nataly.hospital.entity.Contract;
import com.nataly.hospital.entity.PriceList;
import com.nataly.hospital.entity.User;
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
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HospitalApplication.class)
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PriceListRepository priceListRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TimeTableRepository timeTableRepository;
    @Autowired
    private UtilTest utilTest;

    @BeforeEach
    public void clean() {
        userRepository.deleteAll();
        contractRepository.deleteAll();
        priceListRepository.deleteAll();
        accountRepository.deleteAll();
        timeTableRepository.deleteAll();
        resultRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    public void getAllContractTest() throws Exception {
        PriceList service1 = utilTest.createTestPriceList("service1", 1000.00);
        PriceList service2 = utilTest.createTestPriceList("service2", 1500.00);
        PriceList service3 = utilTest.createTestPriceList("service3", 900.00);
        PriceList service4 = utilTest.createTestPriceList("service4", 1100.00);
        List<PriceList> priceList1 = List.of(service1, service2);
        List<PriceList> priceList2 = List.of(service3, service4);
        User patient = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 05, 14),
                "8-904-528-84-45", "asd@ma.ru");
        Contract contract11 = utilTest.createTestContract(LocalDate.of(2022, 11, 14), 11, priceList1, patient);
        Contract contract12 = utilTest.createTestContract(LocalDate.of(2022, 12, 2), 12, priceList2, patient);
        User admin = utilTest.createTestAdmin("Олег", "Иванов", LocalDate.of(1997, 11, 5),
                "8-999-456-00-44", "qwer@ma.ru");
        Account adminAccount = utilTest.createTestAccount("8-999-456-00-44", "admin", admin);

        this.mockMvc.perform(
                        get("/admin/contract/list").with(user(adminAccount))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(contract11, contract12))));
    }

    @Test
    public void getContractByDateAndNumberTest() throws Exception {
        PriceList service1 = utilTest.createTestPriceList("service1", 1000.00);
        PriceList service2 = utilTest.createTestPriceList("service2", 1500.00);
        PriceList service3 = utilTest.createTestPriceList("service3", 900.00);
        PriceList service4 = utilTest.createTestPriceList("service4", 1100.00);
        List<PriceList> priceList1 = List.of(service1, service2);
        List<PriceList> priceList2 = List.of(service3, service4);
        User patient = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 05, 14),
                "8-904-528-84-45", "asd@ma.ru");
        User patient2 = utilTest.createTestPatient("Иван", "Иванов", LocalDate.of(1954, 2, 28),
                "8-994-125-87-99", "yui@ma.ru");
        Contract contract11 = utilTest.createTestContract(LocalDate.of(2022, 11, 14), 11, priceList1, patient);
        Contract contract12 = utilTest.createTestContract(LocalDate.of(2022, 12, 2), 12, priceList2, patient2);
        User admin = utilTest.createTestAdmin("Олег", "Иванов", LocalDate.of(1997, 11, 5),
                "8-999-456-00-44", "qwer@ma.ru");
        Account adminAccount = utilTest.createTestAccount("8-999-456-00-44", "admin", admin);

        this.mockMvc.perform(
                        get("/admin/contract/number").with(user(adminAccount))
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("date", "14.11.2022")
                                .param("number", "11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("number").value(contract11.getNumber()));
    }

    @Test
    public void addContractTest() throws Exception {
        PriceList service1 = utilTest.createTestPriceList("service1", 1000.00);
        PriceList service2 = utilTest.createTestPriceList("service2", 1500.00);
        User patient = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 05, 14),
                "8-904-528-84-45", "asd@ma.ru");
        PatientDto patientDto = new PatientDto(patient.getName(), patient.getSurname(), patient.getPatronymic(),
                patient.getBirthday(), patient.getPhoneNumber(), patient.getEmail());
        User admin = utilTest.createTestAdmin("Олег", "Иванов", LocalDate.of(1997, 11, 5),
                "8-999-456-00-44", "qwer@ma.ru");
        Account adminAccount = utilTest.createTestAccount("8-999-456-00-44", "admin", admin);
        this.mockMvc.perform(
                        post("/admin/contract/add").with(user(adminAccount))
                                .content(objectMapper.writeValueAsString(patientDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("priceList", "service1")
                                .param("priceList", "service2"))
                .andExpect(status().isOk());
        Assertions.assertEquals(2500, contractRepository.findAllByPatientId(patient.getId()).get(0).getCost());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        User patient1 = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 5, 14),
                "8-904-528-84-45", "asd@ma.ru");
        User patient2 = utilTest.createTestPatient("Иван", "Иванов", LocalDate.of(1954, 3, 26),
                "8-923-123-34-45", "a@ma.ru");
        User patient3 = utilTest.createTestPatient("Семен", "Семенов", LocalDate.of(1999, 12, 23),
                "8-966-878-83-12", "sd@ma.ru");
        User patient4 = utilTest.createTestPatient("Ольга", "Петрова", LocalDate.of(1997, 8, 3),
                "8-945-238-56-99", "as@ma.ru");
        User admin = utilTest.createTestAdmin("Олег", "Иванов", LocalDate.of(1997, 11, 5),
                "8-999-456-00-44", "qwer@ma.ru");
        Account adminAccount = utilTest.createTestAccount("8-999-456-00-44", "admin", admin);

        this.mockMvc.perform(
                        get("/admin/users/all").with(user(adminAccount))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(patient1, patient2, patient3, patient4, admin))));
    }

    @Test
    public void getUserByPhoneNumberTest() throws Exception {
        User patient1 = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 5, 14),
                "8-904-528-84-45", "asd@ma.ru");
        User patient2 = utilTest.createTestPatient("Иван", "Иванов", LocalDate.of(1954, 3, 26),
                "8-923-123-34-45", "a@ma.ru");
        User patient3 = utilTest.createTestPatient("Семен", "Семенов", LocalDate.of(1999, 12, 23),
                "8-966-878-83-12", "sd@ma.ru");
        User patient4 = utilTest.createTestPatient("Ольга", "Петрова", LocalDate.of(1997, 8, 3),
                "8-945-238-56-99", "as@ma.ru");
        User admin = utilTest.createTestAdmin("Олег", "Иванов", LocalDate.of(1997, 11, 5),
                "8-999-456-00-44", "qwer@ma.ru");
        Account adminAccount = utilTest.createTestAccount("8-999-456-00-44", "admin", admin);

        this.mockMvc.perform(
                        get("/admin/user/phone").with(user(adminAccount))
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("phoneNumber", "8-904-528-84-45"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(patient1.getName()))
                .andExpect(jsonPath("surname").value(patient1.getSurname()))
                .andExpect(jsonPath("email").value(patient1.getEmail()));
    }

    @Test
    public void getAllByUserRoleTest() throws Exception {
        User patient1 = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 5, 14),
                "8-904-528-84-45", "asd@ma.ru");
        User patient2 = utilTest.createTestPatient("Иван", "Иванов", LocalDate.of(1954, 3, 26),
                "8-923-123-34-45", "a@ma.ru");
        User patient3 = utilTest.createTestPatient("Семен", "Семенов", LocalDate.of(1999, 12, 23),
                "8-966-878-83-12", "sd@ma.ru");
        User admin2 = utilTest.createTestAdmin("Ольга", "Петрова", LocalDate.of(1997, 8, 3),
                "8-945-238-56-99", "as@ma.ru");
        User admin = utilTest.createTestAdmin("Олег", "Иванов", LocalDate.of(1997, 11, 5),
                "8-999-456-00-44", "qwer@ma.ru");
        Account adminAccount = utilTest.createTestAccount("8-999-456-00-44", "admin", admin);

        this.mockMvc.perform(
                        get("/admin/users/type").with(user(adminAccount))
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("role", "PATIENT"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(patient1, patient2, patient3))));
    }

    @Test
    public void addResultTest() throws Exception {
        User patient = utilTest.createTestPatient("Петр", "Петров", LocalDate.of(1987, 5, 14),
                "8-904-528-84-45", "asd@ma.ru");
        ResultDto resultDto = new ResultDto(LocalDate.of(2022, 12, 5), "result description");
        User admin = utilTest.createTestAdmin("Олег", "Иванов", LocalDate.of(1997, 11, 5),
                "8-999-456-00-44", "qwer@ma.ru");
        Account adminAccount = utilTest.createTestAccount("8-999-456-00-44", "admin", admin);
        this.mockMvc.perform(
                        post("/admin/result/add/{id}", patient.getId()).with(user(adminAccount))
                                .content(objectMapper.writeValueAsString(resultDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertEquals(patient.getName(), resultRepository.findAll(patient.getId()).get(0).getUser().getName());
    }

    @Test
    public void addPatientTest() throws Exception {
        PatientDto patientDto = new PatientDto("Петр", "Петров", "", LocalDate.of(1987, 5, 14),
                "8-904-528-84-45", "asd@ma.ru");
        User admin = utilTest.createTestAdmin("Олег", "Иванов", LocalDate.of(1997, 11, 5),
                "8-999-456-00-44", "qwer@ma.ru");
        Account adminAccount = utilTest.createTestAccount("8-999-456-00-44", "admin", admin);
        this.mockMvc.perform(
                        post("/admin/patient/add").with(user(adminAccount))
                                .content(objectMapper.writeValueAsString(patientDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(patientDto.getName()))
                .andExpect(jsonPath("surname").value(patientDto.getSurname()))
                .andExpect(jsonPath("email").value(patientDto.getEmail()));
    }

    @Test
    public void addDoctorTest() throws Exception {
        DoctorDto doctorDto = new DoctorDto("Александр", "Крылов", null, LocalDate.of(1997, 10, 15), "8-904-478-89-56",
                "ert@qw.ru", DoctorPosition.THERAPIST, "doctor description");
        User admin = utilTest.createTestAdmin("Олег", "Иванов", LocalDate.of(1997, 11, 5),
                "8-999-456-00-44", "qwer@ma.ru");
        Account adminAccount = utilTest.createTestAccount("8-999-456-00-44", "admin", admin);
        this.mockMvc.perform(
                        post("/admin/doctor/add").with(user(adminAccount))
                                .content(objectMapper.writeValueAsString(doctorDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(doctorDto.getName()))
                .andExpect(jsonPath("surname").value(doctorDto.getSurname()))
                .andExpect(jsonPath("email").value(doctorDto.getEmail()));
    }
}
