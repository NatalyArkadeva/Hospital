package com.nataly.hospital.service;

import com.nataly.hospital.dto.DoctorDto;
import com.nataly.hospital.entity.Record;
import com.nataly.hospital.entity.TimeTable;
import com.nataly.hospital.entity.User;
import com.nataly.hospital.entity.enums.AppointmentStatus;
import com.nataly.hospital.exception.HospitalException;
import com.nataly.hospital.exception.Message;
import com.nataly.hospital.repository.RecordRepository;
import com.nataly.hospital.repository.TimeTableRepository;
import com.nataly.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final TimeTableRepository timeTableRepository;
    private final UserRepository userRepository;

    public List<LocalDateTime> getAllRecordsByPatient(User patient) {
        List<Record> records = recordRepository.findAllByPatient(patient);
        return records.stream().map(record -> record.getTimeTable().getDateTime())
                .collect(Collectors.toList());
    }

    public void addNewRecordPatient(User patient, DoctorDto doctorDto, LocalDateTime dateTime) {
        User doctor = userRepository.findByNameAndSurnameAndPosition(doctorDto.getName(), doctorDto.getSurname(),
                doctorDto.getPosition().name()).orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
        TimeTable timeTable = timeTableRepository.findByDateTimeAndDoctor(dateTime, doctor.getId());
        timeTable.setStatus(AppointmentStatus.RECORDED);
        timeTableRepository.save(timeTable);
        Record newRecord = new Record(1L, timeTable, patient);
        recordRepository.save(newRecord);
    }
}
