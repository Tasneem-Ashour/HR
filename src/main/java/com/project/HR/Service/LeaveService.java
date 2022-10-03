package com.project.HR.Service;
import com.project.HR.Command.LeaveCommand;
import com.project.HR.Converter.LeaveConverter;
import com.project.HR.DTO.LeaveDto;
import com.project.HR.Entity.Employee;
import com.project.HR.Entity.Leaves;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.Repostory.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class LeaveService {
    @Autowired
    LeaveRepository leaveRepository;
    @Autowired
    LeaveConverter leaveConverter;
    @Autowired
    EmployeeRepository employeeRepository;
    public LeaveDto getEmployeeLeaves(int id) {
        Leaves leaves = leaveRepository.findById(id).get();
        LeaveDto leaveDto = leaveConverter.convertEntityToDto(leaves);
        return leaveDto;
    }
    public LeaveDto recordEmployeeLeave(LeaveCommand leaveCommand) throws Exception {
        if (employeeRepository.findById(leaveCommand.getEmp_id()).isEmpty()) {
            throw new Exception("Employee doesn't exist");
        }
        Employee employee = employeeRepository.findById(leaveCommand.getEmp_id()).get();
        Leaves leaves = leaveConverter.convertCommandToEntity(leaveCommand);
        leaves.setEmployee(employee);
        leaveRepository.save(leaves);
        return leaveConverter.convertEntityToDto(leaves);
    }
    public List<LeaveDto> getEmployeeLeavesByDate(LocalDate date) throws Exception {
        List<Leaves> x = leaveRepository.getLeavesByDate(date);
        List<LeaveDto> leaveDto = new ArrayList<>();
        x.forEach(e -> leaveDto.add(leaveConverter.convertEntityToDto(e)));
        return leaveDto;
    }
}
