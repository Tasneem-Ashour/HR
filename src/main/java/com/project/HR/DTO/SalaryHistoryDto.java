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

//    Date = date
//            Rise = currentRises,
//            Bonus = currentBonuses,
//            Gross = newBaseGross,
//            deduction = 0.15(newBaseGross) + 500,
//    PayRoll = payRoll,
//    LeavesCost = leavesCost

    private LocalDate reportDate;
    private  Double raise;
    private  Double bonus;
    private  Double  deduction ;
    private  Double leavesCost;
    private  Double payRoll;


    }
