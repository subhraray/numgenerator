package com.numbergenerator.Controller;

import com.numbergenerator.Model.Employee;
import com.numbergenerator.Service.CSVService;
import com.numbergenerator.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(EmployeeDataController.class)
class EmployeeDataControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    CSVService fileService;

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("Gopal", 20));
        list.add(new Employee("Raghav", 21));
        list.add(new Employee("Chandra", 22));
        Mockito.when(fileService.getEmployees()).thenReturn(list);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/employees").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.valueOf(200), HttpStatus.valueOf(response.getStatus()));

    }

    @Test
    void updateEmployeeInformation() {
    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void findByName() throws Exception {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("Gopal", 20));
        list.add(new Employee("Goody", 21));

        Mockito.when(employeeService.findByName(Mockito.any(String.class))).thenReturn(list);
        RequestBuilder builder = MockMvcRequestBuilders.get("/api/employee").accept(MediaType.APPLICATION_JSON).param("name","Gopal").contentType(MediaType.APPLICATION_JSON);
        MvcResult result=mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response=result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void createEmployee() throws Exception{

        Employee emp = new Employee("Gopal", 20);
        String empJson = "{\"name\":\"Gopal\",\"age\":20}";

        Mockito.when(employeeService.saveEmployeeData(Mockito.any(Employee.class))).thenReturn(emp);
        RequestBuilder builder = MockMvcRequestBuilders.post("/api/employee").accept(MediaType.APPLICATION_JSON).content(empJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result=mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response=result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}