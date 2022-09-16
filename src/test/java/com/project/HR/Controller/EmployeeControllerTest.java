package com.project.HR.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.HR.Command.EmployeeCommand;
import com.project.HR.Entity.Department;
import com.project.HR.Entity.Employee;
import com.project.HR.Entity.Expertise;
import com.project.HR.Entity.Team;
import com.project.HR.Repostory.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional

public class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void addEmployee_thenReturnStates200() throws Exception {
        Department department = Department.builder().Name("dept1")

                .build();

        Team team = Team.builder().Name("Team1").build();

        Employee manger = Employee.builder()

                .FirstName("manger").LastName("Manger").build();

        EmployeeCommand record = EmployeeCommand.builder().FirstName("tasneem").LastName("essam").DOB(new Date(1 / 6 / 1996)).Graduation(Year.of(2019)).Gender("Female").Salary(20000).departmentId(1).TeamId(1).expertise(new ArrayList<>()).MangerId(1).build();


        Expertise expertise = Expertise.builder().Name("programming Languages").build();
        record.getExpertise().add(expertise);

        this.mockMvc.perform(post("/Employee/add").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(record))).andExpect(status().isOk()).andExpect(jsonPath("$.expertise", notNullValue()));


    }

    @Test
    public void EmployeeMangerRelationTest() {

        Employee manger = Employee

                .builder()

                .FirstName("Manger").LastName("Manger").employeesManger(new ArrayList<>())

                .build();

        Employee employee = Employee.builder()

                .FirstName("emp").LastName("emp").build();


        manger.getEmployeesManger().add(employee);


        employee.setMangerId(manger);
        employeeRepository.save(employee);


        employeeRepository.save(manger);
        Employee dbEmployee = employeeRepository.findById(employee.getId()).get();
        Employee dbManger = employeeRepository.findById(manger.getId()).get();

        Assertions.assertNotNull(dbEmployee);
        Assertions.assertNotNull(dbEmployee.getMangerId());
        Assertions.assertNotNull(dbManger);
        Assertions.assertNull(dbManger.getDepartment());


        Assertions.assertTrue(dbManger.getEmployeesManger().size() > 0);
    }

    @Test
    public void addEmployee_theReturnEmployeeObject() throws Exception {
        Date date = new Date(1996 - 06 - 01);

        EmployeeCommand record = EmployeeCommand.builder().FirstName("tasneem").LastName("essam").DOB(date).Graduation(Year.of(2019)).Salary(20000).departmentId(1).TeamId(1).expertise(new ArrayList<>()).MangerId(1).build();
        record.setDOB(date);

        this.mockMvc.perform(post("/Employee/add").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd")).writeValueAsString(record))).andExpect(jsonPath("$", notNullValue())).andExpect(jsonPath("$.firstName", is("tasneem")))

                .andExpect(jsonPath("$.lastName", is("essam")))

                .andExpect(jsonPath("$.salary", is(20000.0))).andExpect(jsonPath("$.dob").value("1996-06-01")).andExpect(jsonPath("$.graduation", is("2019")))
//                .andExpect(jsonPath("$.mangerId",is()))
//                .andExpect(jsonPath("$.departmentId",is(1)))
                .andExpect(jsonPath("$.teamId", is(1))).andExpect(jsonPath("$.expertise", empty()));


    }

    @Test
    public void addEmployee_withoutdFirstName_shouldReturn() throws Exception {
        EmployeeCommand employeeCommand = EmployeeCommand.builder()

                .Graduation(Year.of(2019)).Gender("Female").Salary(20000).departmentId(1).TeamId(1).expertise(new ArrayList<>()).MangerId(1).build();

        this.mockMvc.perform(post("/Employee/add").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd")).writeValueAsString(employeeCommand))).andExpect(jsonPath("$.firstName", nullValue())).andExpect(jsonPath("$.lastName", nullValue()));


    }

    @Test
    public void getEmployeeDetailsTest_ShouldReturnStatus200() throws Exception {

        mockMvc.perform(get("/Employee/get/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void geEmployee_shouldReturnNotNull() throws Exception {
        mockMvc.perform(get("/Employee/get/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", notNullValue()));
    }


    @Test
    public void geEmployeeDoneNotExist_shouldReturn404() throws Exception {
        mockMvc.perform(get("/Employee/get/1000").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isNotFound());
    }


    @Test
    public void DeleteEmployee_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/Employee/delete/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void DeleteEmployeeThatNotExist_shouldReturnEmployeeNotFound() throws Exception {
        mockMvc.perform(delete("/Employee/delete/1000").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

  @Test
    public void GetEmployeesInTeam_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/Employee/team/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());

  }
  //مفيش تيم
    // مفيش حد في التيم

    public void GetEmployeesInTeamEmpty_ShouldReturn404(){}



}