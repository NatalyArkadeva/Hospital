package com.nataly.hospital;

import com.nataly.hospital.entity.*;
import com.nataly.hospital.entity.enums.AppointmentStatus;
import com.nataly.hospital.entity.enums.DoctorPosition;
import com.nataly.hospital.entity.enums.Role;
import com.nataly.hospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class UtilTest {
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

    public User createTestPatient(String name, String surname, LocalDate birthday, String phoneNumber,
                                  String email) {
        User patient = User.builder()
                .name(name)
                .surname(surname)
                .birthday(birthday)
                .phoneNumber(phoneNumber)
                .email(email)
                .role(Role.PATIENT)
                .build();
        return userRepository.save(patient);
    }

    public User createTestDoctor(String name, String surname, LocalDate birthday, String phoneNumber,
                                 String email, DoctorPosition position,
                                 String doctorDescription) {
        User doctor = User.builder()
                .name(name)
                .surname(surname)
                .birthday(birthday)
                .phoneNumber(phoneNumber)
                .email(email)
                .position(position)
                .doctorDescription(doctorDescription)
                .role(Role.DOCTOR)
                .build();
        return userRepository.save(doctor);
    }

    public Department createTestDepartment(String title, String description) {
        Department department = Department.builder()
                .title(title)
                .description(description)
                .build();
        return departmentRepository.save(department);
    }

    public Result createTestResult(LocalDate date, String description, User patient) {
        Result result = Result.builder()
                .date(date)
                .result(description)
                .user(patient)
                .build();
        return resultRepository.save(result);
    }

    public Contract createTestContract(LocalDate date, int number, List<PriceList> priceList,
                                       User patient) {
        double cost = priceList.stream()
                .map(PriceList::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
        Contract contract = Contract.builder()
                .date(date)
                .number(number)
                .cost(cost)
                .priceList(priceList)
                .patient(patient)
                .build();
        return contractRepository.save(contract);
    }

    public PriceList createTestPriceList(String title, double price) {
        PriceList priceList = PriceList.builder()
                .title(title)
                .price(price)
                .build();
        return priceListRepository.save(priceList);
    }

    public Account createTestAccount(String login, String password, User user) {
        Account account = Account.builder()
                .login(login)
                .password(password)
                .user(user)
                .build();
        return accountRepository.save(account);
    }

    public User createTestAdmin(String name, String surname, LocalDate birthday, String phoneNumber,
                                String email) {
        User admin = User.builder()
                .name(name)
                .surname(surname)
                .birthday(birthday)
                .phoneNumber(phoneNumber)
                .email(email)
                .role(Role.ADMIN)
                .build();
        return userRepository.save(admin);
    }

    public TimeTable createTestTimeTable(LocalDateTime dateTime, int office, User doctor) {
        TimeTable timeTable = TimeTable.builder()
                .dateTime(dateTime)
                .office(office)
                .doctor(doctor)
                .status(AppointmentStatus.FREE)
                .build();
        return timeTableRepository.save(timeTable);
    }

}
