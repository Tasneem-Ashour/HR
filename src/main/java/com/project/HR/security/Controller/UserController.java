package com.project.HR.security.Controller;
import com.project.HR.security.Command.UserCommand;
import com.project.HR.security.Dto.UserDto;
import com.project.HR.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RestControllerAdvice
@RequestMapping(path = "/employee")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> addUser(@RequestBody UserCommand userCommand) throws Exception {
        var output = userService.addUser(userCommand);
        return ResponseEntity.ok(output);
    }

    @GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUser(@PathVariable int id) {
        var output = userService.getUser(id);
        return output;
    }
}
