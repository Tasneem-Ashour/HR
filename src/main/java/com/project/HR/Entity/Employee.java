package com.project.HR.Entity;
import lombok.*;

import javax.persistence.*;
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
    private Employee mangerId;
    @OneToMany(mappedBy = "mangerId")
    private List<Employee> employees = new ArrayList<>();
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "emp_Id")
//    private List<Leave> leave = new ArrayList<>();
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "emp_Id")
//    private List<Raise> raise = new ArrayList<>();
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "emp_Id")
//    private List<Bonus> bonus = new ArrayList<>();
//    private Date hiringDate;
//    @Column(unique=true)
//    private String nationalId ;
//    private String degree;
}
