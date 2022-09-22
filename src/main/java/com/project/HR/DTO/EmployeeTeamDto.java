package com.project.HR.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTeamDto {

    private Integer Id;
    private String FirstName;
    private String LastName;
    private String Gender;
    private String Graduation;
    @JsonFormat( pattern = "yyyy-MM-dd")
    private Date DOB;
    private Double Salary;


}
