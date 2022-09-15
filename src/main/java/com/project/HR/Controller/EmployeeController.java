package com.project.HR.Controller;

import com.project.HR.Command.EmployeeCommand;
import com.project.HR.DTO.EmployeeDto;
import com.project.HR.Entity.Employee;
import com.project.HR.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/Employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping(path = "/getAll/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Employee> getEmployee() {
        return employeeService.getEmployeeDetails();
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeCommand employee) throws Exception {
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }


}
