package com.project.HR.Service;
import com.project.HR.DTO.SalaryHistoryDto;
import com.project.HR.Entity.Employee;
import com.project.HR.Entity.Raises;
import com.project.HR.Repostory.BonusRepository;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.Repostory.LeaveRepository;
import com.project.HR.Repostory.RaisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class SalaryHistoryService {
    @Autowired
    BonusRepository bonusRepository;
    @Autowired
    LeaveRepository leaveRepository;
    @Autowired
    RaisesRepository raisesRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    BonusService bonusService;
    @Autowired
    RaisesService raisesService;
    @Autowired
    LeaveService leaveService;
    public List<SalaryHistoryDto> salaryHistoryDto(String nationalId) throws Exception {
        int totalLeavesCount = 0;
        SalaryHistoryDto current = null;
        Employee employee = employeeRepository.getEmployeeByNationalId(nationalId);
        Double baseGrossSalary = employee.getSalary(); // current Salary
        int employeeId = employee.getId();
        List<Raises> allEmployeeRaises = raisesRepository.getEmployeeRaisesByNationalId(employeeId);
        int i = 0;
        while (i < allEmployeeRaises.size()) {
            baseGrossSalary /= 1 + allEmployeeRaises.indexOf(i);
            i++;
        }
        List<SalaryHistoryDto> result = new ArrayList<>();
        LocalDate initialDate = employee.getHiringDate();
        int limit = employee.getExperience() >= 10 ? 30 : 21;
        while (initialDate.compareTo(LocalDate.now()) < 0) {
            Double currentBonus = bonusService.getEmployeeBonusValueByDateAndEmployeeId(initialDate, employeeId);
            Double currentRaises = raisesService.getEmployeeRaisesValueByDateAndEmployeeId(initialDate, employeeId);
            int currentExtendLeaves = leaveService.getNumberOfLeavesDays(initialDate, employeeId);
            Double newGross = baseGrossSalary + (currentRaises * baseGrossSalary);
            totalLeavesCount += currentExtendLeaves;
            Double leavesCost = 0.0;
            if (limit < totalLeavesCount) {
                leavesCost = (totalLeavesCount - limit) * newGross / 22;
                totalLeavesCount = limit;
            }
            Double payRoll = newGross + currentBonus - leavesCost;
            Double deduction = 0.15 * newGross + 500;
             current = new SalaryHistoryDto(initialDate, currentRaises, currentBonus, deduction, leavesCost, payRoll);
           result.add(current);
           initialDate=initialDate.plusMonths(1);
        }

        return result;
    }
}
