package com.project.HR.Repostory;
import com.project.HR.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("select u from Employee u where u.manager.id = null")
    Employee getTopEmployeeManager();
    @Query("select u from Employee u where u.manager.id =:x")
    List<Employee> getEmployee_ManagerId(@Param("x") int manager);

    @Query("select u from Employee u Where u.nationalId =:n")
    Employee getEmployeeByNationalId(@Param("n") String nationalId);




}
