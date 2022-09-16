package com.project.HR.DTO;

import lombok.*;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {
    private Double net ;
    private Double gross;
}
