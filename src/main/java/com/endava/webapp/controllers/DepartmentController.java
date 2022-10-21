package com.endava.webapp.controllers;

import com.endava.webapp.model.Department;
import com.endava.webapp.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAll() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestBody Department department) {
        Department d = departmentService.addDepartment(department);
        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .build(d.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", uri.toString())
                .body(d);
    }

    @PutMapping("{id}")
    public Department updateDepartment(@RequestBody Department department, @PathVariable Long id) {
        return departmentService.updateDepartment(department, id);
    }
}
