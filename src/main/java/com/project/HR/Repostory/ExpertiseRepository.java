package com.project.HR.Repostory;

import com.project.HR.Entity.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional

public interface ExpertiseRepository extends JpaRepository<Expertise,Integer> {
    @Modifying
    @Query("Delete from Expertise where Emp_Id = :empId")
    public void deleteExpertisesByEmpId(@Param("empId") int empId);
}
