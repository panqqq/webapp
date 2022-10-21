package com.endava.webapp.controllers;

import com.endava.webapp.model.Employee;
import com.endava.webapp.services.EmployeeService;
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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        Employee e = employeeService.add(employee);
        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .build(e.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", uri.toString())
                .body(e);
    }

    @PutMapping("{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        return employeeService.update(employee, id);
    }


}
