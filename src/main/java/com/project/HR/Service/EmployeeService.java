package com.project.HR.Service;
import com.project.HR.Command.EmployeeCommand;
import com.project.HR.Command.EmployeeEditCommand;
import com.project.HR.Converter.DepartmentConverter;
import com.project.HR.Converter.EmployeeConverter;
import com.project.HR.DTO.*;
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
    private final double ratio = 0.85;
    private final int insurance = 500;
    @Autowired
    EmployeeConverter employeeConverter;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    ExpertiseRepository expertiseRepository;
    @Autowired
    DepartmentConverter departmentConverter;
    @Autowired
    DepartmentRepository departmentRepository;

    public EmployeeDto addEmployee(EmployeeCommand employeeCommand) throws Exception {
        if (employeeCommand.getManager() == null && isEmployeeHasNoManger()) {
            throw new Exception("Manager Id can not be null");
        }
        Employee employee = employeeConverter.convertCommandToEntity(employeeCommand);
        Department department = departmentRepository.findById(employeeCommand.getDepartmentId()).get();
        employee.setDepartment(department);
        Team team = teamRepository.findById(employeeCommand.getTeamId()).get();
        employee.setTeam(team);
        Employee manger = getManager(employeeCommand.getManager());
        employee.setManager(manger);
        employee.setExpertise(employeeCommand.getExpertise());
        employee = employeeRepository.save(employee);
        var employeeDto = employeeConverter.covertEntityToDTO(employee);
        employeeDto.setManager(employeeConverter.covertEntityEmployeeToDTO(employee.getManager()));
        employeeDto.setDepartment(departmentConverter.covertEntityToDTO(department));
        return employeeDto;
    }
    private boolean isEmployeeHasNoManger() {
        return employeeRepository.getTopEmployeeManager() !=null;
    }
    private Employee getManager(int manager_Id) {
        if (employeeRepository.findById(manager_Id).isEmpty()) {
            throw new RuntimeException("Manager Id not found ");
        }
        Employee employee = employeeRepository.findById(manager_Id).get();
        return employee;
    }
    public EmployeeDto getEmployeeDetails(int id) throws Exception {
        if (!employeeRepository.findById(id).isPresent()) {
            throw new Exception("Id dose not exist: " + id);
        }
        Employee employee = employeeRepository.findById(id).get();
       Department department= employee.getDepartment();
       DepartmentDto departmentDto = departmentConverter.covertEntityToDTO(department);
        EmployeeDto employeeDto= employeeConverter.covertEntityToDTO(employee);
        employeeDto.setDepartment(departmentDto);

        return employeeDto;
    }
    public void deleteEmployee(int id) throws Exception {
        Employee employee = employeeRepository.findById(id).get();
        Employee manager = employee.getManager();
        if (manager == null) {
            throw new Exception( "Can not delete employee");
        }
        var subEmployees = employeeRepository.getEmployee_ManagerId(manager.getId());
        subEmployees.forEach(e -> e.setManager(manager.getManager()));
        employeeRepository.saveAll(subEmployees);
        employeeRepository.deleteById(id);
    }
    public List<EmployeeTeamDto> getEmployeesInTeam(int id) throws Exception {
        if (teamRepository.findById(id).isEmpty()) {
            throw new Exception("Team dose not exist");
        }
        var team = teamRepository.findById(id).get();
        List<EmployeeTeamDto> employees = new ArrayList<>();
        team.getEmployees().forEach(employee -> employees.add(employeeConverter.convertEntityEmployeeTeamDto(employee)));
        return employees;
    }
    public SalaryDto getSalary(int id) {
        Employee employee = employeeRepository.findById(id).get();
        return new SalaryDto(employee.getSalary() * ratio - insurance, employee.getSalary());
    }
    public void editeEmployeeInfo(EmployeeEditCommand employeeEditCommand) {
        int id = employeeEditCommand.getId();
        Employee employee = employeeRepository.findById(id).get();
        employeeConverter.mapEitDtoToEmployee(employeeEditCommand, employee);
        expertiseRepository.deleteExpertisesByEmpId(id);
        List<Expertise> expertises = employeeEditCommand.getExpertise().stream().map(exp -> new Expertise(null, exp.getName())).collect(Collectors.toList());
        employee.setExpertise(expertises);
        Team team = teamRepository.findById(employeeEditCommand.getTeamId()).get();
        employee.setTeam(team);
        Department department = departmentRepository.findById(employeeEditCommand.getDepartmentId()).get();
        employee.setDepartment(department);
        Employee manager = employeeRepository.findById(employeeEditCommand.getManager()).get();
        employee.setManager(manager);
        employeeRepository.save(employee);
    }
    public List<BasicEmployeeDto> getSubEmloyeesRec(int managerId) throws Exception {
        if (employeeRepository.findById(managerId).isEmpty()) {
            throw new Exception("Id dose not exist: " + managerId);
        }
        List<Employee> employees = new ArrayList<Employee>();
        Employee manager = employeeRepository.findById(managerId).get();
        fillSubEmployees(manager, employees);
        List<BasicEmployeeDto> listDto = new ArrayList<>();
        employees.forEach(e -> listDto.add(employeeConverter.covertEntityBasicEmployeeToDTO(e)));
        return listDto;
    }
    private void fillSubEmployees(Employee manager, List<Employee> employees) {
        //Base Case
        if (manager.getEmployees() == null || manager.getEmployees().size() == 0) return;
        //Core Logic
        employees.addAll(manager.getEmployees());
        //Works as magic
        manager.getEmployees().forEach(employee -> fillSubEmployees(employee, employees));
    }
    public List<BasicEmployeeDto> getSubEmployees(int managerId) throws Exception {
        if (employeeRepository.findById(managerId).isEmpty()) {
            throw new Exception("Id dose not exist: " + managerId);
        }
        Employee manager = employeeRepository.findById(managerId).get();
        List<BasicEmployeeDto> listDto = new ArrayList<>();
        manager.getEmployees().forEach(e -> listDto.add(employeeConverter.covertEntityBasicEmployeeToDTO(e)));
        return listDto;
    }
}
