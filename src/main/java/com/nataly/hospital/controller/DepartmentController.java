package com.nataly.hospital.controller;

import com.nataly.hospital.dto.DepartmentDto;
import com.nataly.hospital.entity.Department;
import com.nataly.hospital.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/all/list_department")
    public List<DepartmentDto> getAllDepartment() {
        return departmentService.getAll();
    }

    @GetMapping("/all/{title}")
    public DepartmentDto getDepartmentByTitle(@PathVariable String title) {
        return departmentService.getDepartmentByTitle(title);
    }

    @PostMapping("/admin/add")
    public ResponseEntity addNewDepartment(@RequestBody Department department) {
        departmentService.addNewDepartment(department);
        return ResponseEntity.status(HttpStatus.OK).body("Department " + department.getTitle() + " was successfully added");
    }
}
