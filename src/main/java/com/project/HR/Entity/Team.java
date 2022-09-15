package com.project.HR.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private  String Name;

    @OneToMany(mappedBy = "team")
    private List<Employee> employees = new ArrayList<>();
}
