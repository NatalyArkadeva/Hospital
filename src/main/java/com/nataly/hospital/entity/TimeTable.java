package com.nataly.hospital.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nataly.hospital.entity.enums.AppointmentStatus;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_tables")
@Builder
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    private int office;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User doctor;

    public TimeTable(@NotNull LocalDateTime dateTime, int office, User doctor) {
        this.dateTime = dateTime;
        this.office = office;
        this.status = AppointmentStatus.FREE;
        this.doctor = doctor;
    }
}
