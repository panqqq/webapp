package com.endava.webapp.services;

import com.endava.webapp.model.Department;
import com.endava.webapp.repositories.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
class DepartmentServiceTest {

    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    void getAllDepartments() {
        Department d1 = new Department(1L, "Java Dev", "Chisinau");
        Department d2 = new Department(2L, "Java Testing", "Bucharest");
        Department d3 = new Department(3L, "DotNet", "London");
        List<Department> departmentList = Arrays.asList(d1, d2, d3);

        when(departmentRepository.findAll()).thenReturn(departmentList);

        assertAll(
                () -> assertThat(departmentService.getAllDepartments()).isEqualTo(departmentList),
                () -> assertThat(departmentService.getAllDepartments().size()).isEqualTo(3),
                () -> assertThat(departmentService.getAllDepartments().isEmpty()).isFalse()
        );

        verify(departmentRepository, times(3)).findAll();

    }

    @Test
    void getDepartment_withUnknownId_throwNoSuchElementException() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> departmentService.getDepartment(1L));

        verify(departmentRepository).findById(1L);
    }

    @Test
    void getDepartment() {
        Department d1 = new Department(1L, "Java Dev", "Chisinau");

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(d1));

        assertThat(departmentService.getDepartment(d1.getId())).isEqualTo(d1);

        verify(departmentRepository).findById(d1.getId());
    }

    @Test
    void addDepartment() {
        Department d1 = new Department(1L, "Java Dev", "Chisinau");

        when(departmentRepository.save(any(Department.class))).thenReturn(d1);

        assertThat(departmentService.addDepartment(d1)).isEqualTo(d1);

        verify(departmentRepository).save(d1);
    }

    @Test
    void updateDepartment_WithUnknownId_throwNoSuchElementException() {
        Department d1 = new Department(1L, "Java Dev", "Chisinau");

        when(departmentRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> departmentService.updateDepartment(d1, 3L));

        verify(departmentRepository).existsById(3L);
    }

    @Test
    void updateDepartment() {
        Department d1 = new Department(1L, "Java Dev", "Chisinau");
        Long id = 3L;

        when(departmentRepository.existsById(anyLong())).thenReturn(true);
        when(departmentRepository.save(any(Department.class))).thenReturn(d1);

        Department updatedDepartment = departmentService.updateDepartment(d1, id);

        assertThat(updatedDepartment).isEqualTo(d1);

        verify(departmentRepository).existsById(id);
        verify(departmentRepository).save(d1);
    }
}