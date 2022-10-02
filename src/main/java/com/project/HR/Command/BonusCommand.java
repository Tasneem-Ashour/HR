package com.project.HR.Command;
import lombok.*;

import java.time.LocalDate;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BonusCommand {
    private LocalDate bonusDate;
    private Double amount;
    private Integer emp_id;
}
