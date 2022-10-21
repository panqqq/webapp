package com.endava.webapp.services;

import com.endava.webapp.model.Employee;
import com.endava.webapp.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("The employee ID " + id + " was not found!"));
    }

    public Employee add(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    public Employee update(Employee employee, Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementException("The employee ID " + id + " was not found!");
        }
        employee.setId(id);
        employeeRepository.save(employee);
        return employee;
    }
}
