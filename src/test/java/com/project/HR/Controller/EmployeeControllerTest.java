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

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        Department department = Department.builder()
                .Name("dept1")

                .build();

        Team team =  Team.builder()
                .Name("Team1")
                .build();

        Employee manger = Employee.builder()

                .FirstName("manger")
                .LastName("Manger")
                .build();

        EmployeeCommand record = EmployeeCommand.builder()
                .FirstName("tasneem")
                .LastName("essam")
              .DOB(new Date(1/6/1996))
                .Graduation(Year.of(2019))
                .Gender("Female")
                .Salary(20000)
               .departmentId(1)
                .TeamId(1)
                .expertise(new ArrayList<>())
                .MangerId(1)
                .build();


        Expertise expertise =  Expertise.builder()
                .Name("programming Languages")
                .build();
        record.getExpertise().add(expertise);

        this.mockMvc.perform(post("/Employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expertise", notNullValue()));


    }

@Test
    public void EmployeeMangerRelationTest(){

        Employee manger = Employee

                .builder()

                .FirstName("Manger")
                .LastName("Manger")
                .employeesManger(new ArrayList<>())

                .build();

        Employee employee = Employee.builder()

                .FirstName("emp")
                .LastName("emp")
                .build();


       manger.getEmployeesManger().add(employee);


//        employee.setManger(manger);
    employeeRepository.save(employee);

//    manger.getEmployeesManger().add(employee);

        employeeRepository.save(manger);
        Employee dbEmployee = employeeRepository.findById(employee.getId()).get();
        Employee dbManger = employeeRepository.findById(manger.getId()).get();

        Assertions.assertNotNull(dbEmployee);
//        Assertions.assertNotNull(dbEmployee.getManger());
    Assertions.assertNotNull(dbManger);
    Assertions.assertNull(dbManger.getDepartment());



        Assertions.assertTrue(dbManger.getEmployeesManger().size()>0);
    }

    @Test
    public void addEmployee_theReturnEmployeeObject() throws Exception {
        EmployeeCommand record = EmployeeCommand.builder().FirstName("tasneem").LastName("essam")
//                .DOB(new Date(1996-6-1))
//                .Graduation(Year.of(2019))
                .Salary(20000).build();

        this.mockMvc.perform(post("/Employee/add").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("tasneem")))

                .andExpect(jsonPath("$.lastName", is("essam")))

                .andExpect(jsonPath("$.salary", is(record.getSalary())));
//                       .andExpect(jsonPath("$.dob", is("1996-1-6")))
//                .andExpect(jsonPath("$.graduation", is("2019")));


    }

    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnStatus200() throws Exception {

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder()
                .FirstName("Ahmad")
                .LastName("Mohamed")
                .Salary(20090.)
                .Id(1).build()
                );

        employeeList.add(Employee.builder()
                .FirstName("Nour")
                .LastName("Mohamed")
                .Salary(900.)
                .Id(2).build()
        );


        employeeRepository.saveAll(employeeList);

        Assertions.assertEquals(2,employeeList.size());
//        EmployeeCommand record = EmployeeCommand.builder()
//                .FirstName("tasneem")
//                .LastName("essam")
//                .Salary(20000).build();
//
//        employeeService.getEmployeeDetails();


//        List<EmployeeCommand> recordList = new ArrayList<>(Collections.singletonList(record));


//        mockMvc.perform(MockMvcRequestBuilders.get("/Employee/getAll")
//
//
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//                .andExpect(jsonPath("$", hasSize(1)));
//                .andExpect(jsonPath("$[0].name", is("tota")));


    }

}