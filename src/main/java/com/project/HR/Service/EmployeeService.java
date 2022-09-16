package com.project.HR.Service;

import com.project.HR.Command.EmployeeCommand;
import com.project.HR.Command.EmployeeEditCommand;
import com.project.HR.Converter.EmployeeConverter;
import com.project.HR.DTO.BasicEmployeeDto;
import com.project.HR.DTO.EmployeeDto;
import com.project.HR.DTO.EmployeeTeamDto;
import com.project.HR.DTO.SalaryDto;
import com.project.HR.Entity.Department;
import com.project.HR.Entity.Employee;
import com.project.HR.Entity.Expertise;
import com.project.HR.Entity.Team;
import com.project.HR.Repostory.DepartmentRepository;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.Repostory.ExpertiseRepository;
import com.project.HR.Repostory.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeConverter employeeConverter;
    @Autowired
    EmployeeRepository employeeRepository;


    @Autowired
    GetEntityFromFiled getEntityFromFiled;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    public EmployeeDto addEmployee(EmployeeCommand employeeCommand) throws Exception {
        if (employeeCommand.getMangerId() == null && employeeRepository.getTopEmployee() != null) {
            return null;

        }
        Employee employee = employeeConverter.convertCommandToEntity(employeeCommand);

        Department department = getEntityFromFiled.getDepartmentFromDeptId(employeeCommand);

        employee.setDepartment(department);
        Team team = getEntityFromFiled.getTeamFromTeamId(employeeCommand);

        employee.setTeam(team);

        Employee manger = getEntityFromFiled.getManager(employeeCommand);
        if (manger == null && employeeCommand.getMangerId() != null) {
            return null;
        }

        employee.setMangerId(manger);


        employee.setExpertise(employeeCommand.getExpertise());


        employee = employeeRepository.save(employee);

        var tasneem = employeeConverter.covertEntityToDTO(employee);
        tasneem.setMangerId(employeeConverter.covertEntityEmployeeToDTO(employee.getMangerId()));
        return tasneem;
    }

    public EmployeeDto getEmployeeDetails(int id) {
        Employee employee = employeeRepository.findById(id).get();

        EmployeeDto employeeDto = employeeConverter.covertEntityToDTO(employee);
        return employeeDto;
    }


    public String deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id).get();
        Employee manager = employee.getMangerId();
        if (manager == null) {
            return "Can not delete employee";
        }
        var subEmployees = employeeRepository.getEmployeesByManagerId(id);
        subEmployees.forEach(e -> e.setMangerId(manager));
        employeeRepository.saveAll(subEmployees);
        employeeRepository.deleteById(id);
        return String.format("employee %s delete successfully", id);
    }

    public List<EmployeeTeamDto> getEmployeesInTeam(int id) {
        var team = teamRepository.findById(id).get();
        List<EmployeeTeamDto> employees = new ArrayList<>();
        team.getEmployees().forEach(employee -> employees.add(employeeConverter.convertEntityEmployeeTeamDto(employee)));

        return employees;
    }

    public SalaryDto getSalary(int id) {
        Employee employee = employeeRepository.findById(id).get();
        return new SalaryDto(employee.getSalary() * 0.85 - 500, employee.getSalary());
    }

    public void editeEmployeeInfo(EmployeeEditCommand employeeEditCommand) {
        int id = employeeEditCommand.getId();
        Employee employee = employeeRepository.findById(id).get();
        employeeConverter.mapEitDtoToEmployee(employeeEditCommand, employee);
        expertiseRepository.deleteExpertisesByEmpId(id);
        List<Expertise> expertises = employeeEditCommand.getExpertise().stream().map(exp -> new Expertise(0, exp)).collect(Collectors.toList());
        employee.setExpertise(expertises);


        Team team = teamRepository.findById(employeeEditCommand.getTeamId()).get();
        employee.setTeam(team);
        Department department = departmentRepository.findById(employeeEditCommand.getDepartmentId()).get();
        employee.setDepartment(department);
        Employee manager = employeeRepository.findById(employeeEditCommand.getMangerId()).get();
        employee.setMangerId(manager);
        employeeRepository.save(employee);


    }

    public List<BasicEmployeeDto> getSubEmloyeesRec(int managerId) {
        List<Employee> employees = new ArrayList<Employee>();
        Employee manager = employeeRepository.findById(managerId).get();
        fillSubEmployees(manager, employees);
        List<BasicEmployeeDto> listDto = new ArrayList<>();
        employees.forEach(e-> listDto.add(employeeConverter.covertEntityBasicEmployeeToDTO(e)));
        return listDto;
    }

    public void fillSubEmployees(Employee manager, List<Employee> employees) {

        //Base Case
        if (manager.getEmployees() == null || manager.getEmployees().size() == 0) return;

        //Core Logic
        employees.addAll(manager.getEmployees());

        //Works as magic
        manager.getEmployees().forEach(employee -> fillSubEmployees(employee, employees));
    }

}
