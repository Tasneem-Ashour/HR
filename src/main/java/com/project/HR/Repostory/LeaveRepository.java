package com.project.HR.Repostory;
import com.project.HR.Entity.Leaves;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Repository
@Transactional
public interface LeaveRepository  extends CrudRepository<Leaves, Integer> {

    @Query(nativeQuery = true, value = "Select *  from leaves  where Month(toDate) = Month(:toDate1) " +
            "And Year(toDate) =  Year(:toDate1)")
    List<Leaves> getLeavesByDate(@Param("toDate1") LocalDate toDate );

//    @Query("select u from Leaves Where u.emp_id =:x")
//    List<Leaves> getAllEmployeeLeaves(@Param("x") int emp_id);
}
