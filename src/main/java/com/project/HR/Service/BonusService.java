package com.project.HR.Service;
import com.project.HR.Command.BonusCommand;
import com.project.HR.Converter.BonusConvertor;
import com.project.HR.DTO.BonusDto;
import com.project.HR.Entity.Bonus;
import com.project.HR.Repostory.BonusRepository;
import com.project.HR.Repostory.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public List<BonusDto> getEmployeeBonusByDateAndEmployeeId(LocalDate date , int emp_id) throws Exception {
        List<Bonus> x = bonusRepository.getBonusByDateAndEmployeeId(date,emp_id);
        List<BonusDto> bonusDto = new ArrayList<>();
        x.forEach(e -> bonusDto.add(bonusConvertor.convertEntityToDto(e)));
        return bonusDto;
    }


    public double getEmployeeBonusValueByDateAndEmployeeId(LocalDate date, int emp_id) throws Exception {
        List<BonusDto> bonusDto = getEmployeeBonusByDateAndEmployeeId(date, emp_id);
        double sum = 0;
        for (BonusDto r : bonusDto) {
            sum += r.getAmount();
        }
        return sum;
    }
}
