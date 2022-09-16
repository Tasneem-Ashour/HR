package com.project.HR.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String FirstName;
    private String LastName;
    private String Gender;
    private Year Graduation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date DOB;
    private Double Salary;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Emp_Id")
    private List<Expertise> expertise = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "Dept_Id", referencedColumnName = "Id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "Team_Id", referencedColumnName = "Id")
    private Team team;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Manger_Id")
    private Employee MangerId;
    @OneToMany(mappedBy = "MangerId")
    private List<Employee> employeesManger = new ArrayList<>();


}
