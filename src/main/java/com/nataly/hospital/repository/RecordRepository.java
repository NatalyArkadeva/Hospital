package com.nataly.hospital.repository;

import com.nataly.hospital.entity.Record;
import com.nataly.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("select r from Record r join r.timeTable t where t.status = 'RECORDED' and " +
            "t.dateTime between :tomorrowDate and :afterTomorrowDate")
    List<Record> findAllTomorrowRecords(LocalDateTime tomorrowDate, LocalDateTime afterTomorrowDate);
    List<Record> findAllByPatient(User patient);
}
