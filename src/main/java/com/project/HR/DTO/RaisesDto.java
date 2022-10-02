package com.project.HR.DTO;
import lombok.*;

import java.time.LocalDate;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RaisesDto {
    private LocalDate raisesDate;
    private Double amount;
}
