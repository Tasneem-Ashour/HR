package com.project.HR.security.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.project.HR.security.Command.UserCommand;
import com.project.HR.security.Entity.Role;
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

import java.util.ArrayList;

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
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    @DatabaseSetup(value = "/dataset/security/user/addUser.xml")
    @ExpectedDatabase(value = "/dataset/security/user/expectedAddUser.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
    public  void addUser() throws Exception {
        UserCommand userCommand = UserCommand.builder()
                .email("user@orange.com")
                .password("1234")
                .roles(new ArrayList<>())
                .employee_id(1)
                .build();
        Role role = Role.builder()
                .id(1)
                .build();
        userCommand.getRoles().add(role);
                this.mockMvc.perform(post("/employee/user")
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .accept(MediaType.APPLICATION_JSON)
                                             .content(objectMapper.writeValueAsString(userCommand)))
                                             .andExpect(status().isOk());


    }
    @Test
    @DatabaseSetup(value = "/dataset/security/user/addUserWithEmployeeDoseNotExist.xml")
    @ExpectedDatabase(value = "/dataset/security/user/expectedAddUserWithEmployeeDoseNotExist.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
    public void addUserWithEmployeeDoseNoTExist() throws Exception {
        UserCommand userCommand = UserCommand.builder()
                .email("user@orange.com")
                .password("1234")
                .roles(new ArrayList<>())
                .employee_id(2)
                .build();
        Role role = Role.builder()
                .id(1)
                .build();
        userCommand.getRoles().add(role);
                     mockMvc.perform(post("/employee/user")
                     .contentType(MediaType.APPLICATION_JSON)
                     .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
    @Test
    @DatabaseSetup(value = "/dataset/security/user/getUserById.xml")
    public  void getUser() throws Exception {
               this.mockMvc.perform(get("/employee/1/user")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)                                    )
                                     .andExpect(status().isOk());


    }



}