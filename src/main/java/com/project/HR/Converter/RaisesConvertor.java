package com.project.HR.Converter;
import com.project.HR.Command.RaisesCommand;
import com.project.HR.DTO.RaisesDto;
import com.project.HR.Entity.Raises;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class RaisesConvertor {
    @Autowired
    ModelMapper mapper;

    public Raises convertCommandToEntity(RaisesCommand raisesCommand) {
//        mapper.getConfiguration().setAmbiguityIgnored(true);
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
//        Raises raises = mapper.map(raisesCommand, Raises.class);
        Raises raises = new Raises();
        raises.setEmp_id(raisesCommand.getEmp_id());
        raises.setAmount(raisesCommand.getAmount());
        raises.setRaisesDate(raisesCommand.getRaisesDate());
        return raises;
    }
    public RaisesDto convertEntityToDto(Raises raises) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        RaisesDto raisesDto = mapper.map(raises, RaisesDto.class);
        return raisesDto;
    }
}
