package com.project.HR.Service;
import com.project.HR.Command.BonusCommand;
import com.project.HR.Converter.BonusConvertor;
import com.project.HR.DTO.BonusDto;
import com.project.HR.Entity.Bonus;
import com.project.HR.Repostory.BonusRepository;
import com.project.HR.Repostory.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BonusService {
    @Autowired
    BonusRepository bonusRepository;
    @Autowired
    BonusConvertor bonusConvertor;
    @Autowired
    EmployeeRepository employeeRepository;
    public BonusDto getEmployeeBonus(int id) throws Exception {
        if (bonusRepository.findById(id).isEmpty()) {
            throw new Exception("id doesn't exist ");
        }
        Bonus bonus = bonusRepository.findById(id).get();
        BonusDto bonusDto = bonusConvertor.convertEntityToDto(bonus);
        return bonusDto;
    }

    public BonusDto addBonus(BonusCommand bonusCommand) throws Exception {
        if (employeeRepository.findById(bonusCommand.getEmp_id()).isEmpty()) {
            throw new Exception("Employee doesn't exist");
        }
        Bonus bonus =bonusConvertor.convertCommandToEntity(bonusCommand);
        bonusRepository.save(bonus);
        return bonusConvertor.convertEntityToDto(bonus);
    }

}
