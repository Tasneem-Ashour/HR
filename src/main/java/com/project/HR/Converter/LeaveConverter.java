package com.project.HR.Converter;
import com.project.HR.Command.LeaveCommand;
import com.project.HR.DTO.LeaveDto;
import com.project.HR.Entity.Leave;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class LeaveConverter {
    @Autowired
    ModelMapper mapper;
    public Leave convertCommandToEntity(LeaveCommand leaveCommand) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        Leave leave = mapper.map(leaveCommand, Leave.class);
        return leave;
    }
    public LeaveDto convertEntityToDto(Leave leave) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        LeaveDto leaveDto = mapper.map(leave, LeaveDto.class);
        return leaveDto;
    }
}
