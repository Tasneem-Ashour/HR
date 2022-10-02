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
public class EmployeeEditCommand {
    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private String graduation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;
    private double salary;
    private Integer departmentId;
    private List<Expertise> expertise;
    private Integer teamId;
    private Integer manager;
    private String nationalId;
    private Integer experience;
    private String degree ;

}
