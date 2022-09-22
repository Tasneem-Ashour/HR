package com.project.HR.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Department")

public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;



    @OneToMany(mappedBy = "department" , cascade = CascadeType.ALL  )
    private List<Employee> employees =new ArrayList<>();


}
