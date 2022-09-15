package com.project.HR.Service;

import com.project.HR.Command.EmployeeCommand;
import com.project.HR.Converter.EmployeeConverter;
import com.project.HR.DTO.EmployeeDto;
import com.project.HR.Entity.Department;
import com.project.HR.Entity.Employee;
import com.project.HR.Entity.Team;
import com.project.HR.Repostory.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    EmployeeConverter employeeConverter;
    @Autowired
    EmployeeRepository employeeRepository;



    @Autowired
    GetEntityFromFiled getEntityFromFiled;




    public EmployeeDto addEmployee(EmployeeCommand employeeCommand) throws Exception {
        Employee employee = employeeConverter.convertCommandToEntity(employeeCommand);

        Department department = getEntityFromFiled.getDepartmentFromDeptId(employeeCommand);

        employee.setDepartment(department);
        Team team = getEntityFromFiled.getTeamFromTeamId(employeeCommand);

        employee.setTeam(team);

        Employee manger = getEntityFromFiled.getListFromMangerId(employeeCommand);
        employee.setMangerId(manger);

       employee.setExpertise(employeeCommand.getExpertise());


        employee = employeeRepository.save(employee);

        var tasneem = employeeConverter.covertEntityToDTO(employee);
        tasneem.setMangerId(employeeConverter.covertEntityEmployeeToDTO(employee.getMangerId()));
        return tasneem;
    }

    public Iterable<Employee> getEmployeeDetails() {

        return employeeRepository.findAll();
    }


//    public EmployeeCommand getDeparmentId(int Id) {
//        var Department = employeeRepostory.findById(Id);
//        return
//    }
}
