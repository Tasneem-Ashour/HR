package com.project.HR.Entity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@Builder
@Data
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Raises")
public class Raises {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id ;
    @Column(name = "raisesdate")

    private LocalDate raisesDate ;
    private Double amount;
    private Integer emp_id;
}


