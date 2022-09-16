package com.project.HR.Repostory;

import com.project.HR.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "select * from Employee where manger_id is null limit 1", nativeQuery = true)
    public Employee getTopEmployee();
}
