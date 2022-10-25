package com.endava.webapp.services;

import com.endava.webapp.model.Employee;
import com.endava.webapp.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void getAllEmployees() {
        Employee e1 = new Employee(1L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);
        Employee e2 = new Employee(2L, "Ron", "Marky", null, "ronmarky@gmail.com", "222", 200d);
        Employee e3 = new Employee(3L, "Luke", "Rice", null, "lukerice@gmail.com", "333", 300d);
        List<Employee> employeeList = Arrays.asList(e1, e2, e3);

        when(employeeRepository.findAll()).thenReturn(employeeList);

        assertThat(employeeService.getAllEmployees()).isEqualTo(employeeList);

        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeeWithUnknownId_throwNoSuchElementException() {
        when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.getEmployee(3L));

        verify(employeeRepository).findById(3L);
    }

    @Test
    void getEmployee() {
        Employee e1 = new Employee(1L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);


        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(e1));

        assertThat(employeeService.getEmployee(1L)).isEqualTo(e1);

        verify(employeeRepository).findById(1L);
    }

    @Test
    void add() {
        Employee e1 = new Employee(1L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);

        when(employeeRepository.save(any(Employee.class))).thenReturn(e1);

        assertThat(employeeService.add(e1)).isEqualTo(e1);

        verify(employeeRepository).save(e1);
    }

    @Test
    void update_UnknownId_throwNoSuchElementException() {
        Employee e1 = new Employee(1L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);

        when(employeeRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> employeeService.update(e1, 3L));

        verify(employeeRepository).existsById(3L);
    }

    @Test
    void update() {
        Employee e1 = new Employee(1L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);
        Long id = 5L;

        when(employeeRepository.existsById(anyLong())).thenReturn(true);
        when(employeeRepository.save(any(Employee.class))).thenReturn(e1);

        Employee updatedEmployee = employeeService.update(e1, id);

        assertThat(updatedEmployee).isEqualTo(e1);

        verify(employeeRepository).existsById(id);
        verify(employeeRepository).save(e1);
    }
}