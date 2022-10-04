package com.project.HR.Service;
import com.project.HR.Command.RaisesCommand;
import com.project.HR.Converter.RaisesConvertor;
import com.project.HR.DTO.RaisesDto;
import com.project.HR.Entity.Employee;
import com.project.HR.Entity.Raises;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.Repostory.RaisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class RaisesService {
    @Autowired
    RaisesRepository raisesRepository;
    @Autowired
    RaisesConvertor raisesConvertor;
    @Autowired
    EmployeeRepository employeeRepository;
    public RaisesDto getEmployeeRaises(int id) throws Exception {
        if (raisesRepository.findById(id).isEmpty()) {
            throw new Exception("id doesn't exist ");
        }
        Raises raises = raisesRepository.findById(id).get();
        RaisesDto raisesDto = raisesConvertor.convertEntityToDto(raises);
        return raisesDto;
    }
    public RaisesDto addRaises(RaisesCommand raisesCommand) throws Exception {
        if (employeeRepository.findById(raisesCommand.getEmp_id()).isEmpty()) {
            throw new Exception("Employee doesn't exist");
        }
        Raises raises = raisesConvertor.convertCommandToEntity(raisesCommand);
        raisesRepository.save(raises);
        Employee employee = employeeRepository.findById(raisesCommand.getEmp_id()).get();
        Double newSalary = employee.getSalary() + (raises.getAmount() * employee.getSalary());
        employee.setSalary(newSalary);
        employeeRepository.save(employee);
        return raisesConvertor.convertEntityToDto(raises);
    }
    public List<RaisesDto> getAllEmployeeRaises(String nationalId) {
        Employee employee = employeeRepository.getEmployeeByNationalId(nationalId);
        var raises = raisesRepository.getEmployeeRaisesByEmp_Id(employee.getId());
        List<RaisesDto> raisesDto = new ArrayList<>();
        raises.forEach(raises1 -> raisesDto.add(raisesConvertor.convertEntityToDto(raises1)));
        return raisesDto;
    }
    public List<RaisesDto> getEmployeeRaisesByDateAndEmployeeId(LocalDate date, int emp_id) throws Exception {
        List<Raises> x = raisesRepository.getRaisesByDateAndEmployeeId(date, emp_id);
        List<RaisesDto> raisesDto = new ArrayList<>();
        x.forEach(e -> raisesDto.add(raisesConvertor.convertEntityToDto(e)));
        return raisesDto;
    }
    public double getEmployeeRaisesValueByDateAndEmployeeId(LocalDate date, int emp_id) throws Exception {
        List<RaisesDto> raisesDto = getEmployeeRaisesByDateAndEmployeeId(date, emp_id);
        double sum = 0;
        for (RaisesDto r : raisesDto) {
            sum += r.getAmount();
        }
        return sum;
    }
}
