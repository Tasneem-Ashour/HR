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
    private  Double raise;
    private  Double bonus;
    private  Double  deduction ;
    private  Double leavesCost;
    private  Double payRoll;


    }
