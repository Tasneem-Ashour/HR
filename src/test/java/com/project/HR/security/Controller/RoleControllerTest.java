package com.project.HR.security.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.project.HR.security.Command.RoleCommand;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@AutoConfigureTestDatabase
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})

class RoleControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DatabaseSetup(value = "/dataset/security/role/addRole.xml")
    @ExpectedDatabase(value = "/dataset/security/role/expectedRole.xml" , assertionMode= DatabaseAssertionMode.NON_STRICT)
    public  void addRole() throws Exception {
        RoleCommand record = RoleCommand.builder()
                .name("Hr")
                .build();
        this.mockMvc.perform(post("/Role/")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(record)))
                                     .andExpect(jsonPath("$.name", Matchers.is("Hr")))
                                      .andExpect(status().isOk())
                                       .andExpect(jsonPath("$.name",Matchers.notNullValue()));


    }





    @Test
    @DatabaseSetup(value = "/dataset/security/role/getAllRole.xml")
    public  void getAllRole() throws Exception {
        this.mockMvc.perform(get("/Role/")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0]name", Matchers.is("Hr")))
                .andExpect(jsonPath("$.[1]name", Matchers.is("Employee")))
                .andExpect(status().isOk());
   }

}


