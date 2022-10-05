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
    final double taxesRatio = 0.15;
    final double insurances = 500;
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
    private static double getBaseGrossSalary(double baseGrossSalary, List<Raises> allEmployeeRaises) {
        int i = 0;
        while (i < allEmployeeRaises.size()) {
            baseGrossSalary /= 1 + allEmployeeRaises.get(i).getAmount();
            i++;
        }
        return baseGrossSalary;
    }
    public List<SalaryHistoryDto> salaryHistoryDto(int employeeId) throws Exception {
        int totalLeavesCount = 0;
        SalaryHistoryDto current = null;
        Employee employee = employeeRepository.findById(employeeId).get();
        double baseGrossSalary = employee.getSalary(); // current Salary
        List<Raises> allEmployeeRaises = raisesRepository.findRaisesByEmp_id(employeeId);

        baseGrossSalary = getBaseGrossSalary(baseGrossSalary, allEmployeeRaises);

        List<SalaryHistoryDto> result = new ArrayList<>();

        LocalDate initialDate = employee.getHiringDate();

        int limit = employee.getExperience() >= 10 ? 30 : 21;

        while (initialDate.compareTo(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1)) < 0) {

            Double currentBonus = bonusService.getEmployeeBonusValueByDateAndEmployeeId(initialDate, employeeId);

            Double currentRaises = raisesService.getEmployeeRaisesValueByDateAndEmployeeId(initialDate, employeeId);

            int currentExtendLeaves = leaveService.getNumberOfLeavesDays(initialDate, employeeId);

             baseGrossSalary = baseGrossSalary + (currentRaises * baseGrossSalary);
            baseGrossSalary= Math.round(baseGrossSalary * 100.0) / 100.0;

            totalLeavesCount += currentExtendLeaves;
            double leavesCost = 0.0;

            if (limit < totalLeavesCount) {
                leavesCost = (totalLeavesCount - limit) * baseGrossSalary / 22;
                totalLeavesCount = limit;
            }
            leavesCost= Math.round(leavesCost * 100.0) / 100.0;


            Double grossAfterLeavesDeduction = baseGrossSalary - leavesCost;

            Double taxes = taxesRatio * grossAfterLeavesDeduction;
            taxes = getaDouble(taxes);

            Double deduction = leavesCost + taxes + insurances;
            deduction= Math.round(deduction * 100.0) / 100.0;

            Double payRoll = baseGrossSalary + currentBonus - deduction;

            current = new SalaryHistoryDto
                    (initialDate,
                     taxes,
                     insurances,
                     leavesCost,
                     deduction,
                     currentRaises,
                     currentBonus,
                     payRoll,
                     baseGrossSalary);
            result.add(current);
            initialDate = initialDate.plusMonths(1);
        }
        return result;
    }
    private static Double getaDouble(Double taxes) {
        taxes = Math.round(taxes * 100.0) / 100.0;
        return taxes;
    }
}
