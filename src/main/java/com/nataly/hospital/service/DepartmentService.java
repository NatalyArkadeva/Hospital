package com.nataly.hospital.service;

import com.nataly.hospital.dto.DepartmentDto;
import com.nataly.hospital.entity.Department;
import com.nataly.hospital.exception.HospitalException;
import com.nataly.hospital.exception.Message;
import com.nataly.hospital.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<DepartmentDto> getAll() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(department -> new DepartmentDto(department.getTitle(), department.getDescription()))
                .collect(Collectors.toList());
    }

    public DepartmentDto getDepartmentByTitle(String title) {
        Department department = departmentRepository.findByTitle(title)
                .orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
        return new DepartmentDto(department.getTitle(), department.getDescription());
    }

    public void addNewDepartment(Department department) {
        if (departmentRepository.findByTitle(department.getTitle()).isPresent()) {
            throw new HospitalException(Message.ALREADY_EXISTS);
        } else {
            departmentRepository.save(department);
        }
    }
}
