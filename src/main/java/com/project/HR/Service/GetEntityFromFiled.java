package com.project.HR.Service;

import com.project.HR.Command.EmployeeCommand;
import com.project.HR.Entity.Department;
import com.project.HR.Entity.Employee;
import com.project.HR.Entity.Team;
import com.project.HR.Repostory.DepartmentRepository;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.Repostory.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetEntityFromFiled {


    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Department getDepartmentFromDeptId(EmployeeCommand employeeCommand){
        int dept_Id = employeeCommand.getDepartmentId();
        Department department = departmentRepository.findById(dept_Id).get();
        return  department;
    }


    public Team getTeamFromTeamId(EmployeeCommand employeeCommand){
        int team_Id = employeeCommand.getTeamId();
        Team team = teamRepository.findById(team_Id).get();
        return  team;
    }


    public Employee getManager(EmployeeCommand employeeCommand){
        Integer manger_Id = employeeCommand.getMangerId();
        if(employeeRepository.findById(manger_Id).isEmpty()){
            return  null;
        }
        Employee employee = employeeRepository.findById(manger_Id).get();
        return  employee;
    }
}
