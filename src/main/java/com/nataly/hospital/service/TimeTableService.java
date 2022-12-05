package com.nataly.hospital.service;

import com.nataly.hospital.entity.TimeTable;
import com.nataly.hospital.entity.enums.AppointmentStatus;
import com.nataly.hospital.repository.TimeTableRepository;
import com.nataly.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeTableService {
    private final TimeTableRepository timeTableRepository;
    private final UserRepository userRepository;

    public void addNewTime(TimeTable newTime) {
        timeTableRepository.save(newTime);
        newTime.getDoctor().getTimeTableList().add(newTime);
        userRepository.save(newTime.getDoctor());
    }

    public Map<LocalDateTime, AppointmentStatus> getTimeByDoctorForWeek(long doctorId) {
        return timeTableRepository.findAllTimeTableByDoctorId(doctorId, LocalDateTime.now(), LocalDateTime.now().plusDays(7))
                .stream()
                .collect(Collectors.toMap(TimeTable::getDateTime, TimeTable::getStatus));
    }
}
