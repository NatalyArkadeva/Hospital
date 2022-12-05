package com.nataly.hospital.dto;

import com.nataly.hospital.entity.enums.DoctorPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthday;
    private String phoneNumber;
    private String email;
    private DoctorPosition position;
    private String doctorDescription;

    public DoctorDto(String name, String surname, String patronymic, DoctorPosition position, String doctorDescription) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.position = position;
        this.doctorDescription = doctorDescription;
    }

}
