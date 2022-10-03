package com.project.HR.Repostory;
import com.project.HR.Entity.Raises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface RaisesRepository extends JpaRepository<Raises , Integer> {
    @Query("select u from Raises u Where u.emp_id =:x")
    List<Raises> getEmployeeRaisesByNationalId(@Param("x") int emp_id);
}
