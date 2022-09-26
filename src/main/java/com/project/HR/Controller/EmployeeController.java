package com.project.HR.Controller;
import com.project.HR.Command.EmployeeCommand;
import com.project.HR.Command.EmployeeEditCommand;
import com.project.HR.DTO.BasicEmployeeDto;
import com.project.HR.DTO.EmployeeDto;
import com.project.HR.DTO.EmployeeTeamDto;
import com.project.HR.DTO.SalaryDto;
import com.project.HR.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RestControllerAdvice
@RequestMapping(path = "/Employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody EmployeeDto getEmployee(@PathVariable int id) throws Exception {
        return employeeService.getEmployeeDetails(id);
    }
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeCommand employee) throws Exception {
        var output = employeeService.addEmployee(employee);
        return ResponseEntity.ok(output);
    }
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) throws Exception {
         employeeService.deleteEmployee(id);
    }
    @GetMapping(path = "/team/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeTeamDto> getAllEmployeesInTeam(@PathVariable int id) throws Exception {
        return employeeService.getEmployeesInTeam(id);
    }
    @GetMapping(path = "/salary/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SalaryDto getSalaryInfo(@PathVariable int id) {
        return employeeService.getSalary(id);
    }
    @PutMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void editEmployee(@RequestBody EmployeeEditCommand employeeEditCommand) {
        employeeService.editeEmployeeInfo(employeeEditCommand);
    }
    @GetMapping(path = "/employeesUnderManager/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BasicEmployeeDto> employeesUnderManager(@PathVariable int id) throws Exception {
        return employeeService.getSubEmloyeesRec(id);
    }
    @GetMapping(path = "/manager/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BasicEmployeeDto> employeesUnderDirectlyManager(@PathVariable int id) throws Exception {
        return employeeService.getSubEmployees(id);
    }
}
