package com.project.HR.Controller;
import com.project.HR.DTO.SalaryHistoryDto;
import com.project.HR.Service.SalaryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RestControllerAdvice
@RequestMapping(path = "/salaryHistory")
public class SalaryHistoryController {

    @Autowired
    SalaryHistoryService salaryHistoryService;

    @GetMapping(value = "/{nationalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List< SalaryHistoryDto> getEmployeeSalaryHistory (@PathVariable  String nationalId) throws Exception {
        return salaryHistoryService.salaryHistoryDto(nationalId);

    }
}
