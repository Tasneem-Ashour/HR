package com.project.HR.Converter;

import com.project.HR.Command.EmployeeCommand;
import com.project.HR.DTO.EmployeeDto;
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
//        mapper.typeMap(Employee.class, EmployeeDto.class).addMappings(m->m.map(s -> s.getMangerId(), EmployeeDto::setMangerId));
        EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);
        return employeeDto;
    }
    public ManagerDto covertEntityEmployeeToDTO(Employee employee) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
//        mapper.typeMap(Employee.class, EmployeeDto.class).addMappings(m->m.map(s -> s.getMangerId(), EmployeeDto::setMangerId));
        ManagerDto managerDto = mapper.map(employee, ManagerDto.class);
        return managerDto;
    }


}
