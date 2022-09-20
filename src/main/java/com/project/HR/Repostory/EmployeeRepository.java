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
    public Employee getTopEmployeeManger();

    @Query(value = "select * from Employee where manger_id = :manger_id", nativeQuery = true)
    public List<Employee> getEmployeesByManagerId(@Param("manger_id") int managerId);
//    List<Employee> findAllEmployee(int managerId);
}
