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

    @Query(value = "select * from Employee where manager_id is null limit 1", nativeQuery = true)
    public Employee getTopEmployeeManager();

    @Query(value = "select * from Employee where manager_Id = :manager_Id", nativeQuery = true)
    public List<Employee> getEmployeesByManagerId(@Param("manager_Id") int managerId);
//    List<Employee> findAllEmployee(int managerId);
}
