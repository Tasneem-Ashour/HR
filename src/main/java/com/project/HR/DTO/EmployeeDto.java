package com.project.HR.DTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.HR.Entity.Expertise;
import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String gender;
    private String graduation;
    @JsonFormat( pattern = "yyyy-MM-dd",timezone = "Africa/Cairo")
    private Date dob;
    private double salary;
    private DepartmentDto department;
    private List<Expertise> expertise;
    private Integer teamId;
    private ManagerDto manager;



}
