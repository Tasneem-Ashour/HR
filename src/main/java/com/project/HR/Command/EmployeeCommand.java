package com.project.HR.Command;

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
public class EmployeeCommand {

    @NonNull
    private String FirstName;
    @NonNull
    private String LastName;
    private String Gender;
    private String Graduation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date DOB;
    private double Salary;
    private Integer departmentId;
    private List<Expertise> expertise;
    private Integer TeamId;
    private Integer MangerId;


}
