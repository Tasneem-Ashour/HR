package com.project.HR.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.project.HR.Command.LeaveCommand;
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
    //    @BeforeEach
//    void setUp() {
//    }
//    @AfterEach
//    void tearDown() {
//    }
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    @DatabaseSetup(value = "dataset/leaves/addLeaves.xml")
//    @ExpectedDatabase(value = "/dataset/expectedAddEmployeeRreturn200.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
   public void addLeave_shouldReturnStatus200() throws Exception {
        LocalDate from= LocalDate.of(2022,10,1);
        LocalDate to= LocalDate.of(2022,10,10);

        LeaveCommand leaveCommand = LeaveCommand.builder()
                .emp_id(1)
                .fromDate(from)
                .toDate(to)
                .build();
        this.mockMvc.perform(post("/Leave")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(leaveCommand)))
                .andExpect(status().isOk());


    }
    @Test
    @DatabaseSetup(value = "dataset/getEmployeeLeaves.xml")
    void getEmployeeLeave_shouldReturnStatus200() throws Exception {
        this.mockMvc.perform(get("/Leave/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                         .andExpect(status().isOk());
    }
}