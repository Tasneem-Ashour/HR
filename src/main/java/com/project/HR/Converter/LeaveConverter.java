package com.project.HR.Converter;
import com.project.HR.Command.LeaveCommand;
import com.project.HR.DTO.LeaveDto;
import com.project.HR.Entity.Leaves;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class LeaveConverter {
    @Autowired
    ModelMapper mapper;
    public Leaves convertCommandToEntity(LeaveCommand leaveCommand) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        Leaves leaves = mapper.map(leaveCommand, Leaves.class);
        return leaves;
    }
    public LeaveDto convertEntityToDto(Leaves leaves) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        LeaveDto leaveDto = mapper.map(leaves, LeaveDto.class);
        return leaveDto;
    }
}
