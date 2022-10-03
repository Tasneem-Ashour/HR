package com.project.HR.Service;
import com.project.HR.Repostory.BonusRepository;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.Repostory.LeaveRepository;
import com.project.HR.Repostory.RaisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
//    public SalaryHistoryDto salaryHistoryDto(String nationalId) {
//        Employee employee = employeeRepository.getEmployeeByNationalId(nationalId);
//       Double baseGrossSalary = employee.getSalary(); // current Salary
//        int employeeId = employee.getId();
//        List<Raises> allEmployeeRaises = raisesRepository.getEmployeeRaisesByNationalId(employeeId);
//        int i=0;
//        while (allEmployeeRaises.size()>0){
//            baseGrossSalary /= 1+ allEmployeeRaises.indexOf(i);
//            i++;
//        }
//    }
}
