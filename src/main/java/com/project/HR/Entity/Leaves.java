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
@Table(name = "leaves")
public class Leaves {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id ;
    @Column(name = "fromdate")
    private LocalDate fromDate ;
    @Column(name = "todate")
    private LocalDate toDate ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id" , referencedColumnName = "id")
    private Employee employee;

}
