package com.project.HR.Command;

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
public class EmployeeCommand {

    private String FirstName;
    private String LastName;
    private String Gender;
    private Year Graduation;
    private Date DOB;
    private double Salary;
    private Integer departmentId;
    private List<Expertise> expertise;
    private Integer TeamId;
    private Integer MangerId;


}
