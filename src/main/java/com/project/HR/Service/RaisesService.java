package com.project.HR.Service;
import com.project.HR.Command.RaisesCommand;
import com.project.HR.Converter.RaisesConvertor;
import com.project.HR.DTO.RaisesDto;
import com.project.HR.Entity.Raises;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.Repostory.RaisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return raisesConvertor.convertEntityToDto(raises);
    }

}
