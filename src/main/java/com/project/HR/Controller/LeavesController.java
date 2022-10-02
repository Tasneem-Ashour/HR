package com.project.HR.Controller;
import com.project.HR.Command.LeaveCommand;
import com.project.HR.DTO.LeaveDto;
import com.project.HR.Service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RestControllerAdvice
@RequestMapping(path = "/Leave")
public class LeavesController {
    @Autowired
    LeaveService leaveService;

    @PostMapping(path = "/" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LeaveDto> addLeave(@RequestBody LeaveCommand leave) throws Exception {
        var output=leaveService.recordEmployeeLeave(leave);
        return ResponseEntity.ok(output);

    }

    @GetMapping(path = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE )
    public  LeaveDto getEmployeeLeave(@PathVariable int id)
    {
        return leaveService.getEmployeeLeaves(id);
    }
}
