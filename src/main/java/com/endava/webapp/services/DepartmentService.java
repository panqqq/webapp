package com.endava.webapp.services;

import com.endava.webapp.model.Department;
import com.endava.webapp.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("The department ID " + id + " was not found!"));
    }

    public Department addDepartment(Department department) {
        Department res = departmentRepository.save(department);
        return res;
    }

    public Department updateDepartment(Department department, Long id) {
        if(!departmentRepository.existsById(id)) {
            throw new NoSuchElementException("The department ID " + id + " was not found!");
        }
        department.setId(id);
        departmentRepository.save(department);
        return department;
    }
}
