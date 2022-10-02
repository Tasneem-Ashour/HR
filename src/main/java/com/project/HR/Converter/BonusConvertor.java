package com.project.HR.Converter;
import com.project.HR.Command.BonusCommand;
import com.project.HR.DTO.BonusDto;
import com.project.HR.Entity.Bonus;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class BonusConvertor {
    @Autowired
    ModelMapper mapper;
    public Bonus convertCommandToEntity(BonusCommand bonusCommand) {

        Bonus bonus = new Bonus();
        bonus.setEmp_id(bonusCommand.getEmp_id());
        bonus.setAmount(bonusCommand.getAmount());
        bonus.setBonusDate(bonusCommand.getBonusDate());
        return  bonus;
    }
    public BonusDto convertEntityToDto(Bonus bonus) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        BonusDto BonusDto = mapper.map(bonus, BonusDto.class);
        return BonusDto;
    }
}
