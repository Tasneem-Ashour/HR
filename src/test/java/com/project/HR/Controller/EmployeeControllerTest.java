package com.project.HR.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.project.HR.Command.EmployeeCommand;
import com.project.HR.Command.EmployeeEditCommand;
import com.project.HR.Entity.Employee;
import com.project.HR.Entity.Expertise;
import com.project.HR.Repostory.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Calendar;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @DatabaseSetup(value = "/dataset/addEmployee.xml")
    @ExpectedDatabase(value = "/dataset/expectedAddEmployeeRreturn200.xml", assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void addEmployee_thenReturnStates200() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996,5,1);
        EmployeeCommand record = EmployeeCommand.builder()
        .firstName("tasneem")
        .lastName("essam")
        .gender("female")
        .teamId(1)
        .departmentId(1)
        .mangerId(1)
        .salary(20000.0)
        .graduation("2019")
        .expertise(new ArrayList<>())
        .dob(calendar.getTime())
        .build();
        Expertise expertise = Expertise.builder()
        .name("java")
                .build();
        record.getExpertise().add(expertise);
        this.mockMvc.perform(post("/Employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk());

    }
    @Test
    public void employeeMangerRelationTest() {
        Employee manger = Employee.builder().firstName("Manger").lastName("Manger").employees(new ArrayList<>()).build();
        Employee employee = Employee.builder().firstName("emp").lastName("emp").build();
        manger.getEmployees().add(employee);
        employee.setMangerId(manger);
        employeeRepository.save(employee);
        employeeRepository.save(manger);
        Employee dbEmployee = employeeRepository.findById(employee.getId()).get();
        Employee dbManger = employeeRepository.findById(manger.getId()).get();
        Assertions.assertNotNull(dbEmployee);
        Assertions.assertNotNull(dbEmployee.getMangerId());
        Assertions.assertNotNull(dbManger);
        Assertions.assertNull(dbManger.getDepartment());
        assertTrue(dbManger.getEmployees().size() > 0);
    }
    @Test
    @DatabaseSetup(value = "/dataset/addEmployee.xml")
    @ExpectedDatabase(value = "/dataset/expectedAddEmployeeRreturnEmployeeObject.xml", assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void addEmployee_theReturnEmployeeObject() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996,Calendar.JUNE,1);
        EmployeeCommand record = EmployeeCommand.builder()
                .firstName("tasneem")
                .lastName("essam")
                .gender("female")
                .teamId(1)
                .departmentId(1)
                .mangerId(1)
                .salary(20000.0)
                .graduation("2019")
                .expertise(new ArrayList<>())
                .dob(calendar.getTime())
                .build();
        Expertise expertise = Expertise.builder()
                .name("java")
                .build();
        record.getExpertise().add(expertise);
        this.mockMvc.perform(post("/Employee/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(jsonPath("$.firstName", is("tasneem")))
                .andExpect(jsonPath("$.lastName", is("essam")))
                .andExpect(jsonPath("$.dob", is("1996-06-01")))
                .andExpect(jsonPath("$.salary", is(20000.0)))
                .andExpect(jsonPath("$.gender", is("female")))
                .andExpect(jsonPath("$.graduation", is("2019")))
                .andExpect(jsonPath("$.teamId", is(1)))
                .andExpect(jsonPath("$.expertise.length()", is(1)))
                .andExpect(jsonPath("$.expertise", hasSize(1)))
                .andExpect(jsonPath("$.department.length()", is(2)));

    }

    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeWithIdExist.xml")
    public void getEmployeeDetailsTest_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/Employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @AfterTransaction
    @DatabaseSetup(value = "/dataset/findEmployeeWithIdExist.xml")
    public void getEmployeeDetailsTest_ShouldReturnEmployeeObject() throws Exception {
        mockMvc.perform(get("/Employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("tasneem")))
                .andExpect(jsonPath("$.lastName", is("essam")))
                .andExpect(jsonPath("$.dob", is("1996-06-01")))
                .andExpect(jsonPath("$.salary", is(20000.0)))
                .andExpect(jsonPath("$.gender", is("female")))
                .andExpect(jsonPath("$.graduation", is("2019")))
                .andExpect(jsonPath("$.teamId", is(1)))
                .andExpect(jsonPath("$.department.length()", is(2)));

    }

    @Test
    @AfterTransaction
    @DatabaseSetup(value = "/dataset/findEmployeeWithIdExist.xml")
    public void geEmployee_shouldReturnNotNull() throws Exception {
        mockMvc.perform(get("/Employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeWithIdExist.xml")
    public void geEmployeeDoneNotExist_shouldException() throws Exception {

        assertThrows(org.springframework.web.util.NestedServletException.class,
                ()->mockMvc.perform(get("/Employee/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)));
    }
    @Test
    @DatabaseSetup(value = "/dataset/deleteEmployeeWithIdExist.xml")
    public void deleteEmployee_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/Employee/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is("employee 2 delete successfully")));
    }
    @Test
    @DatabaseSetup(value = "/dataset/deleteEmployeeWithIdDoesn'tExist.xml")
    public void deleteEmployeeThatNotExist_shouldReturnException() throws Exception {
        assertThrows(org.springframework.web.util.NestedServletException.class,
                ()->mockMvc.perform(delete("/Employee/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
        );

    }
    @Test
    @DatabaseSetup(value = "/dataset/deleteEmployeeDoesn'tHaveManager.xml")
    public void deleteEmployeeThatDoesNotHaveManager_shouldReturnException() throws Exception {
                mockMvc.perform(delete("/Employee/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                                 .andExpect(jsonPath("$",is("Can not delete employee")));



    }
    @Test
    @DatabaseSetup(value = "/dataset/findAllEmployeeInTeam.xml")
    public void getEmployeesInTeam_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/Employee/team/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
@Test
@DatabaseSetup(value = "/dataset/findAllEmployeeInTeam.xml")
    public void getEmployeesInTeam_ShouldContains4EmployeesWithFirstName() throws Exception {
        mockMvc.perform(get("/Employee/team/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[*].firstName",
                        containsInAnyOrder("tasneem", "Mahmoud","Noura","Ahmed")));

    }

    @Test
    @DatabaseSetup(value = "/dataset/findEmployeesInTeamDoesn'tExist.xml")
    public void getEmployeesInTeamEmpty_ShouldReturnException() throws Exception {
        assertThrows(org.springframework.web.util.NestedServletException.class,
                ()-> mockMvc.perform(get("/Employee/team/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        );


    }
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeSalary.xml")
    public void checkEmployeeHasSalaryNetAndGross_shouldBeReturnStatus200() throws Exception {
        mockMvc.perform(get("/Employee/salary/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeGrossSalary.xml")
    public void checkEmployeeHasGrossSalary_shouldBeReturn20000() throws Exception {
        mockMvc.perform(get("/Employee/salary/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gross", is(20000.0)));
    }
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeNetSalary.xml")
    public void checkEmployeeHasNetSalary_whereGrossSalary20000_shouldBeReturn16500() throws Exception {
        mockMvc.perform(get("/Employee/salary/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.net", is(16500.0)));
    }
    @Test
    @DatabaseSetup(value = "/dataset/NoEmployeeFoundToGetSalary.xml")
    public void checkEmployeeNotFoundToGetHisSalary_shouldBeReturnException() throws Exception {
        assertThrows(org.springframework.web.util.NestedServletException.class,
                ()-> mockMvc.perform(get("/Employee/salary/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        );

    }
    @Test
    @DatabaseSetup(value = "/dataset/editeEmployee.xml")
    @ExpectedDatabase(value = "/dataset/expectedEditeEmployee.xml", assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void updateEmployee_thenReturnStates204() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996,5,1);
        EmployeeEditCommand updateEmployee = EmployeeEditCommand.builder()
                .id(4)
                .firstName("Ahmed")
                .lastName("Mohammed")
                .gender("Male")
                .dob( calendar.getTime())
                .graduation("2012")
                .expertise(new ArrayList<>())
                .salary(10000.0)
                .departmentId(1)
                .teamId(1)
                .expertise(new ArrayList<>())
                .mangerId(2)
                .build();
        Expertise expertise = Expertise.builder()
                .name("java")
                .build();
        updateEmployee.getExpertise().add(expertise);
        this.mockMvc.perform(put("/Employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateEmployee)))
                .andExpect(status().isNoContent());

    }
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeUnderMangerRec.xml")
    public void getAllEmployeeUnderManagerRec_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/Employee/employeesUnderManager/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeUnderMangerRec.xml")
    public void getAllEmployeeUnderManagerRec_ShouldReturn3EmployeesWithFirstName() throws Exception {
        mockMvc.perform(get("/Employee/employeesUnderManager/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                         .andExpect(jsonPath("$",hasSize(3)))
                         .andExpect(jsonPath("$[*].firstName",
                          containsInAnyOrder( "Mahmoud","Noura","Ahmed")));    }
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeUnderMangerRec.xml")
    public void getAllEmployeeUnderManagerRec_WhereManagerNotExist_ShouldReturnException() throws Exception {
        assertThrows(org.springframework.web.util.NestedServletException.class,
                ()->  mockMvc.perform(get("/Employee/employeesUnderManager/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
        );

    }
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeesUnderDirectlyManger.xml")
    public void getAllEmployeeUnderDirectlyManager_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/Employee/manager/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DatabaseSetup(value = "/dataset/findEmployeesUnderDirectlyManger.xml")
    public void getAllEmployeeUnderDirectlyManager_ShouldReturn2EmployeeWithCheckedFirstName() throws Exception {
        mockMvc.perform(get("/Employee/manager/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[*].firstName",
                        containsInAnyOrder("Noura","Ahmed")));
    }
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeesUnderDirectlyManger.xml")
    public void getAllEmployeeUnderDirectlyManager_WhereManagerNotExist_ShouldReturnException() throws Exception {
        assertThrows(org.springframework.web.util.NestedServletException.class,
                ()->mockMvc.perform(get("/Employee/manager/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)));
    }
}