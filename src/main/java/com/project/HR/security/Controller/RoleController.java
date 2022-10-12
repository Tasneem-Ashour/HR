package com.project.HR.security.Controller;
import com.project.HR.security.Command.RoleCommand;
import com.project.HR.security.Dto.RoleDto;
import com.project.HR.security.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RestControllerAdvice
@RequestMapping(path = "/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping(path = "/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> addRole(@RequestBody RoleCommand roleCommand){
        var output = roleService.addRole(roleCommand);
                return ResponseEntity.ok(output);
    }

    @GetMapping(path = "/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDto> getAllRecord(){
        var output =roleService.getAllRecord();
        return output;
    }
}
