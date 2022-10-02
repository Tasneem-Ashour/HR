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
@Table(name = "Bonus")
public class Bonus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "bonusdate")
    private LocalDate bonusDate;
    private Double amount;
    private Integer emp_id;
}
