package com.endava.webapp.controllers;

import com.endava.webapp.model.Employee;
import com.endava.webapp.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private EmployeeController employeeController;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void getAll() throws Exception {
        Employee e1 = new Employee(1L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);
        Employee e2 = new Employee(2L, "Ron", "Marky", null, "ronmarky@gmail.com", "222", 200d);
        Employee e3 = new Employee(3L, "Luke", "Rice", null, "lukerice@gmail.com", "333", 300d);
        List<Employee> employeeList = Arrays.asList(e1, e2, e3);

        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/employees"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("[0].id").value(e1.getId()))
                .andExpect(jsonPath("[0]firstName").value(e1.getFirstName()))
                .andExpect(jsonPath("[0]lastName").value(e1.getLastName()))
                .andExpect(jsonPath("[0]email").value(e1.getEmail()))
                .andExpect(jsonPath("[0]phoneNumber").value(e1.getPhoneNumber()))
                .andExpect(jsonPath("[0]department").value(e1.getDepartment()))
                .andExpect(jsonPath("[0]salary").value(e1.getSalary()))
                .andExpect(jsonPath("[1].id").value(e2.getId()))
                .andExpect(jsonPath("[1]firstName").value(e2.getFirstName()))
                .andExpect(jsonPath("[1]lastName").value(e2.getLastName()))
                .andExpect(jsonPath("[1]email").value(e2.getEmail()))
                .andExpect(jsonPath("[1]phoneNumber").value(e2.getPhoneNumber()))
                .andExpect(jsonPath("[1]department").value(e2.getDepartment()))
                .andExpect(jsonPath("[1]salary").value(e2.getSalary()))
                .andExpect(jsonPath("[2].id").value(e3.getId()))
                .andExpect(jsonPath("[2]firstName").value(e3.getFirstName()))
                .andExpect(jsonPath("[2]lastName").value(e3.getLastName()))
                .andExpect(jsonPath("[2]email").value(e3.getEmail()))
                .andExpect(jsonPath("[2]phoneNumber").value(e3.getPhoneNumber()))
                .andExpect(jsonPath("[2]department").value(e3.getDepartment()))
                .andExpect(jsonPath("[2]salary").value(e3.getSalary()));

        verify(employeeService).getAllEmployees();
    }

    @Test
    void getEmployee() throws Exception {
        Employee e = new Employee(1L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);

        when(employeeService.getEmployee(anyLong())).thenReturn(e);

        mockMvc.perform(get("/employees/" + e.getId()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("id").value(e.getId()))
                .andExpect(jsonPath("firstName").value(e.getFirstName()))
                .andExpect(jsonPath("lastName").value(e.getLastName()))
                .andExpect(jsonPath("email").value(e.getEmail()))
                .andExpect(jsonPath("phoneNumber").value(e.getPhoneNumber()))
                .andExpect(jsonPath("department").value(e.getDepartment()))
                .andExpect(jsonPath("salary").value(e.getSalary()));

        verify(employeeService).getEmployee(e.getId());
    }

    @Test
    void addEmployee() throws Exception {
        Employee e1 = new Employee(1L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(e1);

        when(employeeService.add(any(Employee.class))).thenReturn(e1);

        mockMvc.perform(post("/employees")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(jsonPath("id").value(e1.getId()))
                .andExpect(jsonPath("firstName").value(e1.getFirstName()))
                .andExpect(jsonPath("lastName").value(e1.getLastName()))
                .andExpect(jsonPath("email").value(e1.getEmail()))
                .andExpect(jsonPath("phoneNumber").value(e1.getPhoneNumber()))
                .andExpect(jsonPath("department").value(e1.getDepartment()))
                .andExpect(jsonPath("salary").value(e1.getSalary()));

        verify(employeeService).add(e1);
    }

    @Test
    void updateEmployee() throws Exception {
        Employee e1 = new Employee(1L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);
        Employee expected = new Employee(2L, "John", "Doe", null, "johndoe@gmail.com", "111", 100d);
        long id = 3L;
        expected.setId(id);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(e1);

        when(employeeService.update(any(Employee.class), anyLong())).thenReturn(expected);

        mockMvc.perform(put("/employees/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("id").value(expected.getId()))
                .andExpect(jsonPath("firstName").value(expected.getFirstName()))
                .andExpect(jsonPath("lastName").value(expected.getLastName()))
                .andExpect(jsonPath("email").value(expected.getEmail()))
                .andExpect(jsonPath("phoneNumber").value(expected.getPhoneNumber()))
                .andExpect(jsonPath("department").value(expected.getDepartment()))
                .andExpect(jsonPath("salary").value(expected.getSalary()));

        verify(employeeService).update(e1, id);
    }
}