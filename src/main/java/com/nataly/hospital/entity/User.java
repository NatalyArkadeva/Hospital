package com.nataly.hospital.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nataly.hospital.entity.enums.DoctorPosition;
import com.nataly.hospital.entity.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Pattern(regexp = "[А-ЯЁа-яё]+", message = "Name does not much to format")
    private String name;
    @NonNull
    @Pattern(regexp = "[А-ЯЁа-яё]+", message = "Surname does not much to format")
    private String surname;
    @Pattern(regexp = "[А-ЯЁа-яё]+", message = "Patronymic does not much to format")
    private String patronymic;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;
    @NonNull
    @Pattern(regexp = "(8-?\\d{3}-?\\d{3}-?\\d{2}-?\\d{2})", message = "Number does not much to format")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @NonNull
    @Pattern(regexp = "(.*@.*\\.[A-za-z]{1,5})", message = "Email does not much to format")
    @Column(unique = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @Enumerated(EnumType.STRING)
    private DoctorPosition position;
    @Column(name = "doctor_description")
    private String doctorDescription;
    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany
    @JoinColumn(name = "time_table_id")
    @JsonIgnore
    private List<TimeTable> timeTableList;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Account account;

    public User(String name, String surname, String patronymic, LocalDate birthday, String phoneNumber, String email, Department department, DoctorPosition position, String doctorDescription, Role role) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.department = department;
        this.position = position;
        this.doctorDescription = doctorDescription;
        this.role = role;
    }
}
