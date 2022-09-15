package com.project.HR.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "Expertise")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expertise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer Id ;
    String Name ;
//    @ManyToOne
//    @JoinColumn(name = "Emp_Id" ,referencedColumnName="Id")
//    private Employee employee;



}
