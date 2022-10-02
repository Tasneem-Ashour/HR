package com.project.HR.Command;
import lombok.*;

import java.time.LocalDate;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveCommand {
    private Integer emp_id;
    private LocalDate fromDate ;
    private LocalDate toDate ;
}
