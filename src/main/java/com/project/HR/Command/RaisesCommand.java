package com.project.HR.Command;
import lombok.*;

import java.time.LocalDate;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RaisesCommand {
    private Integer emp_id;

    private LocalDate raisesDate ;
    private Double amount;
}
