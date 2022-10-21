package com.endava.webapp.controllers;

import com.endava.webapp.model.Department;
import com.endava.webapp.services.DepartmentService;
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
@ContextConfiguration(classes = DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private DepartmentController departmentController;

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    void getAll() throws Exception {
        Department d1 = new Department(1L, "Java Dev", "Chisinau");
        Department d2 = new Department(2L, "Java Testing", "Bucharest");
        Department d3 = new Department(3L, "DotNet", "London");
        List<Department> departmentList = Arrays.asList(d1, d2, d3);

        when(departmentService.getAllDepartments()).thenReturn(departmentList);

        mockMvc.perform(get("/departments"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("[0].id").value(d1.getId()))
                .andExpect(jsonPath("[0].name").value(d1.getName()))
                .andExpect(jsonPath("[0].location").value(d1.getLocation()))
                .andExpect(jsonPath("[1].id").value(d2.getId()))
                .andExpect(jsonPath("[1].name").value(d2.getName()))
                .andExpect(jsonPath("[1].location").value(d2.getLocation()))
                .andExpect(jsonPath("[2].id").value(d3.getId()))
                .andExpect(jsonPath("[2].name").value(d3.getName()))
                .andExpect(jsonPath("[2].location").value(d3.getLocation()));

        verify(departmentService).getAllDepartments();
    }

    @Test
    void getDepartmentById() throws Exception {
        Department d1 = new Department(1L, "Java Dev", "Chisinau");

        when(departmentService.getDepartment(anyLong())).thenReturn(d1);

        mockMvc.perform(get("/departments/" + d1.getId()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("id").value(d1.getId()))
                .andExpect(jsonPath("name").value(d1.getName()))
                .andExpect(jsonPath("location").value(d1.getLocation()));

        verify(departmentService).getDepartment(d1.getId());
    }

    @Test
    void addDepartment() throws Exception {
        Department d1 = new Department(1L, "Java Dev", "Chisinau");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(d1);

        when(departmentService.addDepartment(any(Department.class))).thenReturn(d1);

        mockMvc.perform(post("/departments")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(jsonPath("id").value(d1.getId()))
                .andExpect(jsonPath("name").value(d1.getName()))
                .andExpect(jsonPath("location").value(d1.getLocation()));

        verify(departmentService).addDepartment(d1);
    }

    @Test
    void updateDepartment() throws Exception {
        Department d1 = new Department(1L, "Java Dev", "Chisinau");
        Department expected = new Department(2L, "Java Dev", "Chisinau");
        long id = 3L;
        expected.setId(id);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(d1);

        when(departmentService.updateDepartment(any(Department.class), anyLong())).thenReturn(expected);

        mockMvc.perform(put("/departments/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("id").value(expected.getId()))
                .andExpect(jsonPath("name").value(expected.getName()))
                .andExpect(jsonPath("location").value(expected.getLocation()));

        verify(departmentService).updateDepartment(d1, id);
    }
}