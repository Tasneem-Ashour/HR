package com.project.HR.security.Dto;
import com.project.HR.DTO.EmployeeNameDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private List<RoleDto> roleDto = new ArrayList<>();
    private EmployeeNameDto employeeNameDto;

}
