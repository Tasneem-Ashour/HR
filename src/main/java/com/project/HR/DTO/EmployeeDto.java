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

    private String FirstName;
    private String LastName;
    private String Gender;
    private String Graduation;
    @JsonFormat( pattern = "yyyy-MM-dd")
    private Date DOB;
    private double Salary;
    private DepartmentDto department;
    private List<Expertise> expertise;
    private Integer TeamId;
    private ManagerDto MangerId;



}
