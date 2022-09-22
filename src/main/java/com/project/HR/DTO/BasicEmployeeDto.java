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
    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private Year graduation;
    @JsonFormat( pattern = "yyyy-MM-dd",timezone = "Africa/Cairo")
    private Date dob;
    private double salary;

}
