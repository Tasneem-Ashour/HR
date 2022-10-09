package com.project.HR.security.Command;
import com.project.HR.security.Entity.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCommand {
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private Integer employee_id;
}
