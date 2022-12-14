package com.project.HR.Entity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private String graduation;
    private Date dob;
    private Double salary;
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
    @JoinColumn(name = "manager_Id")
    private Employee manager;
    @OneToMany(mappedBy = "manager")
    private List<Employee> employees = new ArrayList<>();
    @Column(unique = true)
    @Pattern(regexp = "^([1-9]{1})([0-9]{2})([0-9]{2})([0-9]{2})([0-9]{2})[0-9]{3}([0-9]{1})[0-9]{1}$",message = "national id not correct")
    private String nationalId;
    private Integer experience;
    private String degree;
    @Column(name = "hiringdate")
    private LocalDate hiringDate;


}
