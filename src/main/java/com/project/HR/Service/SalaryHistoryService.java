package com.project.HR.Service;
import com.project.HR.DTO.SalaryHistoryDto;
import com.project.HR.Entity.Employee;
import com.project.HR.Entity.Raises;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.Repostory.RaisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class SalaryHistoryService {
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
    public List<SalaryHistoryDto> salaryHistoryDto(String nationalId) throws Exception {
        final  double insurance = 500;

        final double taxesRatio =0.15;
        int totalLeavesCount = 0;
        SalaryHistoryDto current = null;

        Employee employee = employeeRepository.getEmployeeByNationalId(nationalId);

        double baseGrossSalary = employee.getSalary(); // current Salary

        int employeeId = employee.getId();

        List<Raises> allEmployeeRaises = raisesRepository.getEmployeeRaisesByEmp_Id(employeeId);

        baseGrossSalary = getBaseGrossSalary(baseGrossSalary, allEmployeeRaises);

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

            Double taxes = taxesRatio * newGross ;

            current = new SalaryHistoryDto(initialDate,taxes,insurance,leavesCost,currentRaises,currentBonus,payRoll);

            result.add(current);

            initialDate = initialDate.plusMonths(1);
        }
        return result;
    }
}
