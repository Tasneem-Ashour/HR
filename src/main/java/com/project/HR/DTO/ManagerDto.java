package com.project.HR.DTO;

import lombok.*;

import java.util.Date;

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
    private String Graduation;
    private Date DOB;
    private double Salary;
//    private Department department;
//    private List<Expertise> expertise;
    private Integer TeamId;
}
