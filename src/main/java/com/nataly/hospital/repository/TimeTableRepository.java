package com.nataly.hospital.repository;

import com.nataly.hospital.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    @Query("select t from TimeTable t join t.doctor d where d.id = :id " +
            "and t.status = 'FREE' and t.dateTime between :nowDate and :afterWeekDate")
    List<TimeTable> findAllTimeTableByDoctorId(long id, LocalDateTime nowDate, LocalDateTime afterWeekDate);

    TimeTable findByDateTimeAndDoctor(LocalDateTime date, long doctorId);
}
