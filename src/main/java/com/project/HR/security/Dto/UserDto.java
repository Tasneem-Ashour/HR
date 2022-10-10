package com.project.HR.security.Dto;
import com.project.HR.DTO.EmployeeNameDto;
import lombok.*;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private RoleDto roleDto;
    private EmployeeNameDto employeeNameDto;

}
