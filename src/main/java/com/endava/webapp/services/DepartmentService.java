package com.endava.webapp.services;

import com.endava.webapp.model.Department;
import com.endava.webapp.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return (List<Department>) departmentRepository.findAll();
    }

    public Department getDepartment(final Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("The department ID " + id + " was not found!"));
    }

    public Department addDepartment(final Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(final Department department, Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new NoSuchElementException("The department ID " + id + " was not found!");
        }
        department.setId(id);
        departmentRepository.save(department);
        return department;
    }
}
