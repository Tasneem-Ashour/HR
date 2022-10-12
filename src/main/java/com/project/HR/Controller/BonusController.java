package com.project.HR.Controller;
import com.project.HR.Command.BonusCommand;
import com.project.HR.DTO.BonusDto;
import com.project.HR.Service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RestControllerAdvice
@RequestMapping(path = "/bonus")
public class BonusController {
    @Autowired
    BonusService bonusService;
    @PostMapping(path = "/" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BonusDto> addBonus(@RequestBody BonusCommand bonusCommand) throws Exception {
        var output=bonusService.addBonus(bonusCommand);
        return ResponseEntity.ok(output);

    }

    @GetMapping(path = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE )
    public  BonusDto getEmployeeBonus(@PathVariable int id) throws Exception {
        return bonusService.getEmployeeBonus(id);
    }

    @GetMapping(path = "/local-Date" , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BonusDto> listOfBonus (@RequestParam("localDate")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate , @RequestParam("emp_id") int emp_id) throws Exception {
        return  bonusService.getEmployeeBonusByDateAndEmployeeId(localDate,emp_id);

    }

}
