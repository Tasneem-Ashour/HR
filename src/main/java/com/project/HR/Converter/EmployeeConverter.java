package com.project.HR.Converter;

import com.project.HR.Command.EmployeeCommand;
import com.project.HR.Command.EmployeeEditCommand;
import com.project.HR.DTO.BasicEmployeeDto;
import com.project.HR.DTO.EmployeeDto;
import com.project.HR.DTO.EmployeeTeamDto;
import com.project.HR.DTO.ManagerDto;
import com.project.HR.Entity.Employee;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {
    @Autowired
    ModelMapper modelmapper;

    public Employee convertCommandToEntity(EmployeeCommand employeeCommand) {
        modelmapper.getConfiguration().setAmbiguityIgnored(true);

        Employee employee = modelmapper.map(employeeCommand, Employee.class);
        return employee;
    }

    public EmployeeCommand covertEntityToCommand(Employee employee) {
        modelmapper.getConfiguration().setAmbiguityIgnored(true);

        EmployeeCommand employeeCommand = modelmapper.map(employee, EmployeeCommand.class);
        return employeeCommand;
    }


    public EmployeeDto covertEntityToDTO(Employee employee) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);
        return employeeDto;
    }
    public ManagerDto covertEntityEmployeeToDTO(Employee employee) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ManagerDto managerDto = mapper.map(employee, ManagerDto.class);
        return managerDto;
    }

    public EmployeeTeamDto convertEntityEmployeeTeamDto(Employee employee){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        EmployeeTeamDto employeeTeamDto = mapper.map(employee, EmployeeTeamDto.class);
        return employeeTeamDto;
    }

    public void mapEitDtoToEmployee(EmployeeEditCommand s, Employee d){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
         mapper.map(s, d);

    }
    public BasicEmployeeDto covertEntityBasicEmployeeToDTO(Employee employee) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        BasicEmployeeDto basicEmployeeDto = mapper.map(employee, BasicEmployeeDto.class);
        return basicEmployeeDto;
    }


}
