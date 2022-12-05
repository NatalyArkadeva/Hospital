package com.nataly.hospital.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "results")
@Builder
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
    @NonNull
    private String result;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
