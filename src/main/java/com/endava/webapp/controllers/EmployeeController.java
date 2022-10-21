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
    public ResponseEntity<List<Employee>> getAll() {
        final List<Employee> employeeList = employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employeeList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        final Employee employee = employeeService.getEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        final Employee e = employeeService.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        final Employee updatedEmployee = employeeService.update(employee, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }


}
