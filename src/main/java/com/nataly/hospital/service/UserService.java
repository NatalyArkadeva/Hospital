package com.nataly.hospital.service;

import com.nataly.hospital.dto.DoctorDto;
import com.nataly.hospital.dto.PatientDto;
import com.nataly.hospital.entity.Account;
import com.nataly.hospital.entity.User;
import com.nataly.hospital.entity.enums.Role;
import com.nataly.hospital.exception.HospitalException;
import com.nataly.hospital.exception.Message;
import com.nataly.hospital.repository.AccountRepository;
import com.nataly.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
    }

    public List<User> getAllByUserRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    public void editPatient(Account account, PatientDto patientDto) {
        account.getUser().setPhoneNumber(patientDto.getPhoneNumber());
        account.getUser().setEmail(patientDto.getEmail());
        account.setLogin(patientDto.getPhoneNumber());
        userRepository.save(account.getUser());
        accountRepository.save(account);
    }

    public void editDoctor(Account account, DoctorDto doctorDto) {
        account.getUser().setPosition(doctorDto.getPosition());
        account.getUser().setDoctorDescription(doctorDto.getDoctorDescription());
        userRepository.save(account.getUser());
    }

    public User addPatient(PatientDto patientDto) {
        User patient = User.builder()
                .name(patientDto.getName())
                .surname(patientDto.getSurname())
                .birthday(patientDto.getBirthday())
                .phoneNumber(patientDto.getPhoneNumber())
                .email(patientDto.getEmail())
                .role(Role.PATIENT)
                .build();
        return userRepository.save(patient);
    }

    public User addDoctor(DoctorDto doctorDto) {
        User doctor = User.builder()
                .name(doctorDto.getName())
                .surname(doctorDto.getSurname())
                .birthday(doctorDto.getBirthday())
                .phoneNumber(doctorDto.getPhoneNumber())
                .email(doctorDto.getEmail())
                .position(doctorDto.getPosition())
                .doctorDescription(doctorDto.getDoctorDescription())
                .role(Role.DOCTOR)
                .build();
        return userRepository.save(doctor);
    }
}
