package com.project.HR.DTO;
import lombok.*;

import java.time.LocalDate;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryHistoryDto {
    private LocalDate reportDate;
    private Double taxes;
    private Double insurances;
    private Double leavesCost;
    private Double deduction;
    private Double raises;
    private Double bonus;
    private Double payRoll;
    private  Double gross;
}
