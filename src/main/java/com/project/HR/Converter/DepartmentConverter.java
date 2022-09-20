package com.project.HR.Converter;
import com.project.HR.DTO.DepartmentDto;
import com.project.HR.Entity.Department;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
@Component
public class DepartmentConverter {

    public DepartmentDto covertEntityToDTO(Department department ){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        DepartmentDto departmentDto = mapper.map(department, DepartmentDto.class);
        return departmentDto;
    }
}
