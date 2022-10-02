package com.project.HR.Service;
import com.project.HR.Command.LeaveCommand;
import com.project.HR.Converter.LeaveConverter;
import com.project.HR.DTO.LeaveDto;
import com.project.HR.Entity.Leave;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.Repostory.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LeaveService {
    @Autowired
    LeaveRepository leaveRepository;
    @Autowired
    LeaveConverter leaveConverter;
    @Autowired
    EmployeeRepository employeeRepository;
    //todo check if form -- to not null
    //todo calc balance accourding to experience 21 or 30 days
    public LeaveDto getEmployeeLeaves(int id) {
        Leave leave = leaveRepository.findById(id).get();
        LeaveDto leaveDto = leaveConverter.convertEntityToDto(leave);
        return leaveDto;
    }
    public LeaveDto recordEmployeeLeave(LeaveCommand leaveCommand) throws Exception {
        if (employeeRepository.findById(leaveCommand.getEmp_id()).isEmpty()) {
            throw new Exception("Employee doesn't exist");
        }
        Leave leave = leaveConverter.convertCommandToEntity(leaveCommand);
        leaveRepository.save(leave);
        return leaveConverter.convertEntityToDto(leave);
    }
}
