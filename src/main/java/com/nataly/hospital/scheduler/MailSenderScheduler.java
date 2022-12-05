package com.nataly.hospital.scheduler;

import com.nataly.hospital.entity.Record;
import com.nataly.hospital.repository.RecordRepository;
import com.nataly.hospital.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@RequiredArgsConstructor
public class MailSenderScheduler {

    private final NotificationService notificationService;
    private final RecordRepository recordRepository;

    @Scheduled(cron = "0 0 07 * * ?")
    public void scheduledSendMail() {
        List<Record> records = recordRepository.findAllTomorrowRecords(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        for (Record record : records) {
            notificationService.sendMail(record.getPatient().getEmail(), "Запись на приём к врачу",
                    "Завтра вы записаны на прием к " + record.getTimeTable().getDoctor().getPosition() + " " +
                            record.getTimeTable().getDoctor().getSurname() + " " +
                            record.getTimeTable().getDoctor().getName() + " " +
                            record.getTimeTable().getDateTime());
        }
    }
}
