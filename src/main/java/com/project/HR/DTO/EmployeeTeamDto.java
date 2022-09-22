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

    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private String graduation;
    @JsonFormat( pattern = "yyyy-MM-dd",timezone = "Africa/Cairo")
    private Date dob;
    private Double salary;


}
