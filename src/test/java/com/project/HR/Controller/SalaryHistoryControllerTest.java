package com.project.HR.Controller;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
class SalaryHistoryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/salaryHistory.xml")
    public void getSalaryHistory() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isOk());
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/salaryHistory.xml")
    public void salaryHistoryListSizeCheck() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                                    .andExpect(jsonPath("$", hasSize(9)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/raises/salaryWithOneRaisesInMonth.xml")
    public void salaryWithRaiseOneMonth() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*]raises", contains(0.5,0.0)))
                .andExpect(jsonPath("$.[*].gross", contains(20000.0 ,30000.0)))
                .andExpect(jsonPath("$.[0].gross", is(20000.0)))
                .andExpect(jsonPath("$.[1].gross", is(30000.0)))

                .andExpect(jsonPath("$.[0].deduction", is(3500.0)))
                .andExpect(jsonPath("$.[1].deduction", is(5000.0)))

                .andExpect(jsonPath("$.[*].payRoll", contains(16500.0,25000.0)))
                .andExpect(jsonPath("$.[0].leavesCost", is(0.0)))
                .andExpect(jsonPath("$.[0].taxes", is(3000.0)))
                .andExpect(jsonPath("$.[0].insurances", is(500.0)))
                .andExpect(jsonPath("$.[0].bonus", is(0.0)));
    }
    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/raises/salaryWithMultiRaisesInMonth.xml")
    public void salaryWithMultiRaisesInOneMonth() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*]raises", contains(0.6,0.0)))
                .andExpect(jsonPath("$.[*].gross", contains(18181.82,29090.91)))
                .andExpect(jsonPath("$.[0].deduction", is(3227.27)))
                .andExpect(jsonPath("$.[0].payRoll", is(14954.55)))
                .andExpect(jsonPath("$.[0].leavesCost", is(0.0)))
                .andExpect(jsonPath("$.[0].taxes", is(2727.27)))
                .andExpect(jsonPath("$.[0].insurances", is(500.0)))
                .andExpect(jsonPath("$.[0].bonus", is(0.0)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/employeeThatHaveOnlyGrossSalary.xml")
    public void salaryWithOneRaiseInMonth() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].deduction", is(3500.0)))
                .andExpect(jsonPath("$.[0].payRoll", is(16500.0)))
                .andExpect(jsonPath("$.[0].leavesCost", is(0.0)))
                .andExpect(jsonPath("$.[0].taxes", is(3000.0)))
                .andExpect(jsonPath("$.[0].insurances", is(500.0)))
                .andExpect(jsonPath("$.[0].bonus", is(0.0)))
                .andExpect(jsonPath("$.[0].raises", is(0.0)))
                .andExpect(jsonPath("$.[0].gross", is(20000.0)));

    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/bonus/salaryWithOneBonusInMonth.xml")
    public void salaryWithOneBonusInMonth() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].deduction", is(3500.0)))
                .andExpect(jsonPath("$.[0].payRoll", is(36500.0)))
                .andExpect(jsonPath("$.[0].leavesCost", is(0.0)))
                .andExpect(jsonPath("$.[0].taxes", is(3000.0)))
                .andExpect(jsonPath("$.[0].insurances", is(500.0)))
                .andExpect(jsonPath("$.[0].bonus", is(20000.0)))
                .andExpect(jsonPath("$.[0].raises", is(0.0)))
                .andExpect(jsonPath("$.[0].gross", is(20000.0)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/bonus/salaryWithMultiBonusImMonth.xml")
    public void salaryWithMultiBonusInMonth() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].deduction", is(3500.0)))
                .andExpect(jsonPath("$.[0].payRoll", is(56500.0)))
                .andExpect(jsonPath("$.[0].leavesCost", is(0.0)))
                .andExpect(jsonPath("$.[0].taxes", is(3000.0)))
                .andExpect(jsonPath("$.[0].insurances", is(500.0)))
                .andExpect(jsonPath("$.[0].bonus", is(40000.0)))
                .andExpect(jsonPath("$.[0].raises", is(0.0)))
                .andExpect(jsonPath("$.[0].gross", is(20000.0)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/bonus/salaryWithOneBonusInYear.xml")
    public void salaryWithMultiBonusInYear() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].deduction", containsInRelativeOrder(3500.0)))
                .andExpect(jsonPath("$.[*].leavesCost", containsInRelativeOrder(0.0)))
                .andExpect(jsonPath("$.[*].taxes", containsInRelativeOrder(3000.0)))
                .andExpect(jsonPath("$.[*].insurances", containsInRelativeOrder(500.0)))
                .andExpect(jsonPath("$.[*].bonus", contains(20000.0, 20000.0, 100000.0)))
                .andExpect(jsonPath("$.[*].raises", containsInRelativeOrder(0.0)))
                .andExpect(jsonPath("$.[*].gross", containsInRelativeOrder(20000.0)))
                .andExpect(jsonPath("$.[*].payRoll", contains(36500.0,36500.0, 116500.0)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/leaves/salaryWithLeavesInOneMonthYear10.xml")
    public void salaryWithLeavesInMonthWithExpertenceBiggerThan10Years() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].deduction", is(3500.0)))
                .andExpect(jsonPath("$.[0].payRoll", is(16500.0)))
                .andExpect(jsonPath("$.[0].leavesCost", is(0.0)))
                .andExpect(jsonPath("$.[0].taxes", is(3000.0)))
                .andExpect(jsonPath("$.[0].insurances", is(500.0)))
                .andExpect(jsonPath("$.[0].bonus", is(0.0)))
                .andExpect(jsonPath("$.[0].raises", is(0.0)))
                .andExpect(jsonPath("$.[0].gross", is(20000.0)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/leaves/salaryWithLeavesInOneMonthYear2.xml")
    public void salaryWithLeavesInMonthWithExperienceLessThan10Years() throws Exception {
        this.mockMvc.perform(get("/salaryHistory/10")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].deduction", is(3500.0)))
                .andExpect(jsonPath("$.[0].payRoll", is(16500.0)))
                .andExpect(jsonPath("$.[0].leavesCost", is(0.0)))
                .andExpect(jsonPath("$.[0].taxes", is(3000.0)))
                .andExpect(jsonPath("$.[0].insurances", is(500.0)))
                .andExpect(jsonPath("$.[0].bonus", is(0.0)))
                .andExpect(jsonPath("$.[0].raises", is(0.0)))
                .andExpect(jsonPath("$.[0].gross", is(20000.0)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/leaves/salaryWithLeavesMore21DaysAndLess10Years.xml")
    public void salaryWithLeavesMore21Days() throws Exception {
        this.mockMvc.
         perform(get("/salaryHistory/10")
        .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].deduction", contains( 3500.0,3500.0,9681.82 ,5818.18)))
                .andExpect(jsonPath("$.[*].leavesCost", contains(0.0, 0.0, 7272.73,2727.27)))
                .andExpect(jsonPath("$.[*].taxes", contains(3000.0 ,3000.0 ,1909.09,2590.91 )))
                .andExpect(jsonPath("$.[*].insurances", containsInRelativeOrder(500.0)))
                .andExpect(jsonPath("$.[*].bonus", containsInRelativeOrder(0.0)))
                .andExpect(jsonPath("$.[*].raises", containsInRelativeOrder(0.0)))
                .andExpect(jsonPath("$.[*].gross", contains(20000.0 ,20000.0,20000.0,20000.0)))
                .andExpect(jsonPath("$.[*].payRoll", contains(16500.0,16500.0,10318.18,14181.82)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/leaves/salaryWithLeavesMoreThan30Days.xml")
    public void salaryWithLeavesMore30Days() throws Exception {
        this.mockMvc.
                        perform(get("/salaryHistory/10")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].deduction", contains( 3500.0,3500.0,3500.0 ,5045.45)))
                .andExpect(jsonPath("$.[*].leavesCost", contains(0.0, 0.0,0.0,1818.18)))
                .andExpect(jsonPath("$.[*].taxes", contains(3000.0 ,3000.0 ,3000.0,2727.27 )))
                .andExpect(jsonPath("$.[*].insurances", containsInRelativeOrder(500.0)))
                .andExpect(jsonPath("$.[*].bonus", containsInRelativeOrder(0.0)))
                .andExpect(jsonPath("$.[*].raises", containsInRelativeOrder(0.0)))
                .andExpect(jsonPath("$.[*].gross", contains(20000.0 ,20000.0,20000.0,20000.0)))
                .andExpect(jsonPath("$.[*].payRoll", contains(16500.0,16500.0,16500.0,14954.55)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/salaryWithLeavesAndRaise.xml")
    public void salaryWithLeavesAndRaise() throws Exception {
        this.mockMvc.
                        perform(get("/salaryHistory/10")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].deduction", contains( 2500.0 ,5818.18)))
                .andExpect(jsonPath("$.[*].leavesCost", contains(0.0, 2727.27)))
                .andExpect(jsonPath("$.[*].taxes", contains(2000.0 ,2590.91 )))
                .andExpect(jsonPath("$.[*].insurances", containsInRelativeOrder(500.0)))
                .andExpect(jsonPath("$.[*].bonus", contains(0.0,0.0)))
                .andExpect(jsonPath("$.[*].raises", contains(0.5,0.0)))
                .andExpect(jsonPath("$.[*].gross", contains(13333.33 ,20000.0)))
                .andExpect(jsonPath("$.[*].payRoll", contains(10833.33,14181.82)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/salaryWithLeavesAndBonus.xml")
    public void salaryWithLeavesAndBonus() throws Exception {
        this.mockMvc.
                        perform(get("/salaryHistory/10")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].deduction", contains( 3500.0 ,5818.18)))
                .andExpect(jsonPath("$.[*].leavesCost", contains(0.0, 2727.27)))
                .andExpect(jsonPath("$.[*].taxes", contains(3000.0 ,2590.91 )))
                .andExpect(jsonPath("$.[*].insurances", containsInRelativeOrder(500.0)))
                .andExpect(jsonPath("$.[*].bonus", contains(0.0,20000.0)))
                .andExpect(jsonPath("$.[*].raises", contains(0.0,0.0)))
                .andExpect(jsonPath("$.[*].gross", contains(20000.0 ,20000.0)))
                .andExpect(jsonPath("$.[*].payRoll", contains(16500.0,34181.82)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/salaryWithRaiseAndBonus.xml")
    public void salaryWithRaiseAndBonus() throws Exception {
        this.mockMvc.
                        perform(get("/salaryHistory/10")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].deduction", contains( 2500.0 ,3500.0)))
                .andExpect(jsonPath("$.[*].leavesCost", contains(0.0, 0.0)))
                .andExpect(jsonPath("$.[*].taxes", contains(2000.0 ,3000.0 )))
                .andExpect(jsonPath("$.[*].insurances", containsInRelativeOrder(500.0)))
                .andExpect(jsonPath("$.[*].bonus", contains(0.0,20000.0)))
                .andExpect(jsonPath("$.[*].raises", contains(0.5,0.0)))
                .andExpect(jsonPath("$.[*].gross", contains(13333.33 ,20000.0)))
                .andExpect(jsonPath("$.[*].payRoll", contains(10833.33,36500.0)));
    }

    @Test
    @DatabaseSetup(value = "/dataset/salaryHistory/salaryWithRaiseAndBonusAndLeaves.xml")
    public void salaryWithRaiseAndBonusAndLeaves() throws Exception {
        this.mockMvc.
                        perform(get("/salaryHistory/10")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].deduction", contains( 2500.0 ,5818.18)))
                .andExpect(jsonPath("$.[*].leavesCost", contains(0.0, 2727.27)))
                .andExpect(jsonPath("$.[*].taxes", contains(2000.0 ,2590.91 )))
                .andExpect(jsonPath("$.[*].insurances", containsInRelativeOrder(500.0)))
                .andExpect(jsonPath("$.[*].bonus", contains(0.0,20000.0)))
                .andExpect(jsonPath("$.[*].raises", contains(0.5,0.0)))
                .andExpect(jsonPath("$.[*].gross", contains(13333.33 ,20000.0)))
                .andExpect(jsonPath("$.[*].payRoll", contains(10833.33,34181.82)));
    }

}