package com.project.HR.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEditeDto {

    private Integer Id;
    private String FirstName;
    private String LastName;
    private String Gender;
    private Year Graduation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date DOB;
    private Double Salary;

    private List<String> expertise = new ArrayList<>();

    private Integer departmentId;

    private Integer teamId;

    private Integer MangerId;

}
