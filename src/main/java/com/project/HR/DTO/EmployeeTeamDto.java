package com.project.HR.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;
import java.time.Year;

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
    private Year Graduation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date DOB;
    private Double Salary;


}
