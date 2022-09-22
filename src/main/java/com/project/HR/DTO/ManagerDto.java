package com.project.HR.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDto {

    private String firstName;
    private String lastName;
    private String gender;
    private String graduation;
    @JsonFormat( pattern = "yyyy-MM-dd",timezone = "Africa/Cairo")
    private Date dob;
    private double salary;
    private Integer teamId;
}
