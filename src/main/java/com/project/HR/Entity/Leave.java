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
@Table(name = "`Leaves`")
public class Leave {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id ;
    @Column(name = "fromdate")
    private LocalDate fromDate ;
    @Column(name = "todate")
    private LocalDate toDate ;
    private Integer emp_id;

}
