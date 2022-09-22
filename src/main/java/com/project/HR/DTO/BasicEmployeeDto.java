package com.project.HR.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Year;
import java.util.Date;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicEmployeeDto {
    private Integer Id;
    private String FirstName;
    private String LastName;
    private String Gender;
    private Year Graduation;
    @JsonFormat( pattern = "yyyy-MM-dd")
    private Date DOB;
    private double Salary;

}
