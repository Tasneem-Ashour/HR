package com.project.HR.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.project.HR.Command.EmployeeCommand;
import com.project.HR.Command.EmployeeEditCommand;
import com.project.HR.Entity.Expertise;
import com.project.HR.Repostory.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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
// start from 10
    @Test
    @DatabaseSetup(value = "/dataset/addEmployee.xml")
    @ExpectedDatabase(value = "/dataset/expectedAddEmployeeRreturn200.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void addEmployee_thenReturnStates200() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996,Calendar.JUNE,1);
        LocalDate date = LocalDate.of(2020,01,01);
        EmployeeCommand record = EmployeeCommand.builder()
                .firstName("Hady")
                .lastName("Alex")
                .gender("male")
                .teamId(10)
                .departmentId(10)
                .manager(11)
                .salary(20000.0)
                .graduation("2020")
                .expertise(new ArrayList<>())
                .dob(calendar.getTime())
                .degree("senior")
                .experience(10)
                .nationalId("123450789")
                .hiringDate(date)
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
    @DatabaseSetup(value = "/dataset/addEmployee.xml")
    @ExpectedDatabase(value = "/dataset/expectedAddEmployeeRreturnEmployeeObject.xml", assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void addEmployee_theReturnEmployeeObject() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996,Calendar.JUNE,1);
        LocalDate date = LocalDate.of(2020,01,01);

        EmployeeCommand record = EmployeeCommand.builder()
                .firstName("Hady")
                .lastName("Alex")
                .gender("male")
                .teamId(10)
                .departmentId(10)
                .manager(11)
                .salary(20000.0)
                .graduation("2020")
                .expertise(new ArrayList<>())
                .dob(calendar.getTime())
                .degree("senior")
                .experience(10)
                .nationalId("123450789")
                .hiringDate(date)
                .build();
        Expertise expertise = Expertise.builder()
                .name("java")
                .build();
        record.getExpertise().add(expertise);
        this.mockMvc.perform(post("/Employee/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(jsonPath("$.firstName", is("Hady")))
                .andExpect(jsonPath("$.lastName", is("Alex")))
                .andExpect(jsonPath("$.dob", is("1996-06-01")))
                .andExpect(jsonPath("$.salary", is(20000.0)))
                .andExpect(jsonPath("$.gender", is("male")))
                .andExpect(jsonPath("$.graduation", is("2020")))
                .andExpect(jsonPath("$.teamId", is(10)))
                .andExpect(jsonPath("$.expertise.length()", is(1)))
                .andExpect(jsonPath("$.expertise", hasSize(1)))
                .andExpect(jsonPath("$.department.length()", is(2)))
                .andExpect(jsonPath("$.degree", is ("senior")))
                .andExpect(jsonPath("$.experience", is(10)))
                .andExpect(jsonPath("$.nationalId", is("123450789")));
//                .andExpect(jsonPath("$.hiringdate", is("2020-01-01")));



    }
//start from 100
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeWithIdExist.xml")
    public void getEmployeeDetailsTest_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/Employee/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    
    @DatabaseSetup(value = "/dataset/findEmployeeWithIdExist.xml")
    public void getEmployeeDetailsTest_ShouldReturnEmployeeObject() throws Exception {
        mockMvc.perform(get("/Employee/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("tasneem")))
                .andExpect(jsonPath("$.lastName", is("essam")))
                .andExpect(jsonPath("$.dob", is("1996-06-01")))
                .andExpect(jsonPath("$.salary", is(20000.0)))
                .andExpect(jsonPath("$.gender", is("female")))
                .andExpect(jsonPath("$.graduation", is("2019")))
                .andExpect(jsonPath("$.teamId", is(100)))
                .andExpect(jsonPath("$.department.length()", is(2)))
                .andExpect(jsonPath("$.degree", is ("senior")))
                .andExpect(jsonPath("$.experience", is(10)))
                .andExpect(jsonPath("$.nationalId", is("123456789")));
//                .andExpect(jsonPath("$.hiringDate",is("2020-01-01")));

    }

    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeWithIdExist.xml")
    public void geEmployee_shouldReturnNotNull() throws Exception {
        mockMvc.perform(get("/Employee/100")
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
    @ExpectedDatabase(value = "/dataset/expectedDeleteEmployeeWithIdExist.xml", assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void deleteEmployee_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/Employee/201")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
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
    //range 300
    @Test
    @DatabaseSetup(value = "/dataset/deleteEmployeeDoesn'tHaveManager.xml")
    public void deleteEmployeeThatDoesNotHaveManager_shouldReturnException() throws Exception {

        assertThrows(org.springframework.web.util.NestedServletException.class,
                ()-> mockMvc.perform(delete("/Employee/300")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
        );




    }
    @Test
    @DatabaseSetup(value = "/dataset/findAllEmployeeInTeam.xml")
    public void getEmployeesInTeam_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/Employee/team/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
@Test

@DatabaseSetup(value = "/dataset/findAllEmployeeInTeam.xml")
    public void getEmployeesInTeam_ShouldContains4EmployeesWithFirstName() throws Exception {
        mockMvc.perform(get("/Employee/team/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[*].firstName",
                        containsInAnyOrder("tasneem", "Mahmoud","Noura","Ahmed")));

    }
//1000
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
        mockMvc.perform(get("/Employee/salary/900")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeGrossSalary.xml")
    public void checkEmployeeHasGrossSalary_shouldBeReturn20000() throws Exception {
        mockMvc.perform(get("/Employee/salary/950")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gross", is(20000.0)));
    }
    //2000
    @Test
    @DatabaseSetup(value = "/dataset/findEmployeeNetSalary.xml")
    public void checkEmployeeHasNetSalary_whereGrossSalary20000_shouldBeReturn16500() throws Exception {
        mockMvc.perform(get("/Employee/salary/2000")
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
    //3000
    @Test
    @DatabaseSetup(value = "/dataset/editeEmployee.xml")
    @ExpectedDatabase(value = "/dataset/expectedEditeEmployee.xml", assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void updateEmployee_thenReturnStates204() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996,5,1);
        EmployeeEditCommand updateEmployee = EmployeeEditCommand.builder()
                .id(3002)
                .firstName("Ahmed")
                .lastName("Mohammed")
                .gender("Male")
                .dob( calendar.getTime())
                .graduation("2012")
                .expertise(new ArrayList<>())
                .salary(10000.0)
                .departmentId(3000)
                .teamId(3000)
                .manager(3000)
                .expertise(new ArrayList<>())
                .degree("senior")
                .experience(10)
                .nationalId("123056789")
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
        mockMvc.perform(get("/Employee/employeesUnderManager/4000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    
    @DatabaseSetup(value = "/dataset/findEmployeeUnderMangerRec.xml")
    public void getAllEmployeeUnderManagerRec_ShouldReturn3EmployeesWithFirstName() throws Exception {
        mockMvc.perform(get("/Employee/employeesUnderManager/4000")
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
    //5000
    @Test
    
    @DatabaseSetup(value = "/dataset/findEmployeesUnderDirectlyManger.xml")
    public void getAllEmployeeUnderDirectlyManager_ShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/Employee/manager/5000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DatabaseSetup(value = "/dataset/findEmployeesUnderDirectlyManger.xml")
    public void getAllEmployeeUnderDirectlyManager_ShouldReturn2EmployeeWithCheckedFirstName() throws Exception {
        mockMvc.perform(get("/Employee/manager/5001")
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