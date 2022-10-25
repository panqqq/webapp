package com.endava.webapp.repositories;

import com.endava.webapp.model.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
