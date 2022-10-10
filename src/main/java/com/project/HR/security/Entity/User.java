package com.project.HR.security.Entity;
import com.project.HR.Entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_credential")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @UniqueEmail
//    @NotBlank(message = "Email is mandatory")
    @Column(unique=true)
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "employee_id" ,insertable=false, updatable=false)
    private Employee employee;
    private Integer employee_id;
}
