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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RestControllerAdvice
@RequestMapping(path = "/Employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody EmployeeDto getEmployee( @PathVariable int id) {
        try {        return employeeService.getEmployeeDetails(id);


        }
        catch (Exception exception){  throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id Not Found",exception);
        }
    }



    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeCommand employee) throws Exception {
      var output=employeeService.addEmployee(employee);
      if(output==null){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
        return ResponseEntity.ok(output);
    }

    @DeleteMapping(path = "/delete/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public  String deleteEmployee(@PathVariable int id ){
        try {
            return employeeService.deleteEmployee( id );
        }
        catch (Exception exception){  throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Id Not Found",exception);
        }
    }

    @GetMapping(path = "/team/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeTeamDto> getAllEmployeesInTeam(@PathVariable int id ){
        return employeeService.getEmployeesInTeam(id);
    }

    @GetMapping(path = "/salary/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public SalaryDto getSalaryInfo(@PathVariable int id){
        return employeeService.getSalary(id);
    }

    @PutMapping(path = "/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void editEmployee(@RequestBody EmployeeEditCommand employeeEditCommand ){

        employeeService.editeEmployeeInfo(employeeEditCommand);
    }

    @GetMapping(path = "/employeesUnderManager/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BasicEmployeeDto> employeesUnderManager(@PathVariable int id ){
      return   employeeService.getSubEmloyeesRec(id);
    }





}
