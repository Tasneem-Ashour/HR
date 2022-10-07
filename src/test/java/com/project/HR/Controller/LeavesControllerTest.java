package com.project.HR.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.project.HR.Command.LeaveCommand;
import org.hamcrest.Matchers;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class LeavesControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DatabaseSetup(value = "/dataset/leaves/addLeaves.xml")
    @ExpectedDatabase(value = "/dataset/leaves/expectedLeaves.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void addLeavefromDateProperty() throws Exception {
        LocalDate from= LocalDate.of(2022,10,1);
        LocalDate to= LocalDate.of(2022,10,10);
        LeaveCommand leaveCommand = LeaveCommand.builder()
                .emp_id(100)
                .fromDate(from)
                .toDate(to)
                .build();
        this.mockMvc.perform(post("/Leave/")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(leaveCommand)))
                .andExpect(jsonPath("$.fromDate", Matchers.is("2022-10-01")));
    }

    @Test
    @DatabaseSetup(value = "/dataset/leaves/addLeaves.xml")
    @ExpectedDatabase(value = "/dataset/leaves/expectedLeaves.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void addLeavefromDatePropertyNotNULL() throws Exception {
        LocalDate from= LocalDate.of(2022,10,1);
        LocalDate to= LocalDate.of(2022,10,10);
        LeaveCommand leaveCommand = LeaveCommand.builder()
                .emp_id(100)
                .fromDate(from)
                .toDate(to)
                .build();
        this.mockMvc.perform(post("/Leave/")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(leaveCommand)))
                .andExpect(jsonPath("$.fromDate", Matchers.notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "/dataset/leaves/addLeaves.xml")
    @ExpectedDatabase(value = "/dataset/leaves/expectedLeaves.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void addLeavetoDatePropertyNotNull() throws Exception {
        LocalDate from= LocalDate.of(2022,10,1);
        LocalDate to= LocalDate.of(2022,10,10);
        LeaveCommand leaveCommand = LeaveCommand.builder()
                .emp_id(100)
                .fromDate(from)
                .toDate(to)
                .build();
        this.mockMvc.perform(post("/Leave/")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(leaveCommand)))
                .andExpect(jsonPath("$.toDate", Matchers.notNullValue()));
    }

    @Test
    @DatabaseSetup(value = "/dataset/leaves/addLeaves.xml")
    @ExpectedDatabase(value = "/dataset/leaves/expectedLeaves.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void addLeavetoDateProperty() throws Exception {
        LocalDate from= LocalDate.of(2022,10,1);
        LocalDate to= LocalDate.of(2022,10,10);
        LeaveCommand leaveCommand = LeaveCommand.builder()
                .emp_id(100)
                .fromDate(from)
                .toDate(to)
                .build();
        this.mockMvc.perform(post("/Leave/")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(leaveCommand)))
                .andExpect(jsonPath("$.toDate", Matchers.is("2022-10-10")));
    }




    @Test
    @DatabaseSetup(value = "/dataset/leaves/addLeaves.xml")
    @ExpectedDatabase(value = "/dataset/leaves/expectedLeaves.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
   public void addLeave_shouldReturnStatus200() throws Exception {
        LocalDate from= LocalDate.of(2022,10,1);
        LocalDate to= LocalDate.of(2022,10,10);
        LeaveCommand leaveCommand = LeaveCommand.builder()
                .emp_id(100)
                .fromDate(from)
                .toDate(to)
                .build();
        this.mockMvc.perform(post("/Leave/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(leaveCommand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromDate", Matchers.is("2022-10-01")));
        ;


    }
    @Test
    @DatabaseSetup("/dataset/leaves/getEmployeeLeaves.xml")
    void getEmployeeLeave_shouldReturnStatus200() throws Exception {
        this.mockMvc.perform(get("/Leave/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



}