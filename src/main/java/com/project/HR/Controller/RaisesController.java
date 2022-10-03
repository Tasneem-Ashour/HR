package com.project.HR.Controller;
import com.project.HR.Command.RaisesCommand;
import com.project.HR.DTO.RaisesDto;
import com.project.HR.Service.RaisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RestControllerAdvice
@RequestMapping(path = "/Raises")
public class RaisesController {
    @Autowired
    RaisesService raisesService;

    @PostMapping(path = "/" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RaisesDto> addRaises(@RequestBody RaisesCommand raises) throws Exception {
        var output=raisesService.addRaises(raises);
        return ResponseEntity.ok(output);

    }

    @GetMapping(path = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE )
    public  RaisesDto getEmployeeRaise(@PathVariable int id) throws Exception {
        return raisesService.getEmployeeRaises(id);
    }


    @GetMapping(path = "/employee/{nationalId}" , produces = MediaType.APPLICATION_JSON_VALUE )
    public List<RaisesDto> getAllEmployeeRaises(@PathVariable String nationalId) throws Exception {
        return raisesService.getAllEmployeeRaises(nationalId);
    }

}
