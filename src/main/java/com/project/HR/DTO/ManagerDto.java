package com.project.HR.DTO;

import com.project.HR.Entity.Expertise;
import lombok.*;

import java.time.Year;
import java.util.Date;
import java.util.List;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDto {

    private String FirstName;
    private String LastName;
    private String Gender;
    private Year Graduation;
    private Date DOB;
    private double Salary;
    //    private Department department;
    private List<Expertise> expertise;
    private Integer TeamId;
}
