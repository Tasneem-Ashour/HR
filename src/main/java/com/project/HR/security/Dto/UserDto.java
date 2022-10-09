package com.project.HR.security.Dto;
import com.project.HR.DTO.EmployeeDto;
import com.project.HR.security.Entity.Role;
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
    private Integer id;
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private EmployeeDto employeeDto;

}
