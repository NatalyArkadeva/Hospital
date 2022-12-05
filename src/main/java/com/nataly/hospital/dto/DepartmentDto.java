package com.nataly.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class DepartmentDto {
    private String title;
    private String description;
}
