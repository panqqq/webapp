package com.endava.webapp.controllers;

import com.endava.webapp.model.Department;
import com.endava.webapp.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        final List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.status(HttpStatus.OK).body(departments);
    }

    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable final Long id) {
        final Department department = departmentService.getDepartment(id);
        return ResponseEntity.status(HttpStatus.OK).body(department);
    }

    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody final Department department) {
        final Department addedDepartment = departmentService.addDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedDepartment);
    }

    @PutMapping("{id}")
    public ResponseEntity<Department> updateDepartment(@RequestBody final Department department, @PathVariable final Long id) {
        final Department updateDepartment = departmentService.updateDepartment(department, id);
        return ResponseEntity.status(HttpStatus.OK).body(updateDepartment);
    }
}
