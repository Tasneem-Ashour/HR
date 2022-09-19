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
    TeamRepository teamRepository;

    @Autowired
    ExpertiseRepository expertiseRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    private final double ratio =0.85;
    private final int insurance = 500;

    public EmployeeDto addEmployee(EmployeeCommand employeeCommand) throws Exception {
        if (employeeCommand.getMangerId() == null && isEmployeeHasNoManger()) {
            throw  new Exception("Manager Id can not be null");
        }
        Employee employee = employeeConverter.convertCommandToEntity(employeeCommand);
        Department department = departmentRepository.findById(employeeCommand.getDepartmentId()).get();
        employee.setDepartment(department);
        Team team = teamRepository.findById(employeeCommand.getTeamId()).get();
        employee.setTeam(team);
        Employee manger = getManager(employeeCommand.getMangerId());
        employee.setMangerId(manger);
        employee.setExpertise(employeeCommand.getExpertise());
        employee = employeeRepository.save(employee);
        var employeeDto = employeeConverter.covertEntityToDTO(employee);
        employeeDto.setMangerId(employeeConverter.covertEntityEmployeeToDTO(employee.getMangerId()));
        return employeeDto;
    }

    private boolean isEmployeeHasNoManger() {
        return employeeRepository.getTopEmployeeManger() != null;
    }

    private Employee getManager(int manger_Id){
        if(employeeRepository.findById(manger_Id).isEmpty()){
            throw new RuntimeException("Manager Id not found ");
        }
        Employee employee = employeeRepository.findById(manger_Id).get();
        return  employee;
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
        var subEmployees = employeeRepository.getEmployeesByManagerId(manager.getId());
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
        return new SalaryDto(employee.getSalary() * ratio -insurance, employee.getSalary());
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

    private void fillSubEmployees(Employee manager, List<Employee> employees) {

        //Base Case
        if (manager.getEmployees() == null || manager.getEmployees().size() == 0) return;

        //Core Logic
        employees.addAll(manager.getEmployees());

        //Works as magic
        manager.getEmployees().forEach(employee -> fillSubEmployees(employee, employees));
    }

    public List<BasicEmployeeDto> getSubEmployees(int managerId) {
        Employee manager = employeeRepository.findById(managerId).get();
        List<BasicEmployeeDto> listDto = new ArrayList<>();
        manager.getEmployees().forEach(e-> listDto.add(employeeConverter.covertEntityBasicEmployeeToDTO(e)));
        return listDto;
    }
}
