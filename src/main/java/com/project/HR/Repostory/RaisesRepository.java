package com.project.HR.Repostory;
import com.project.HR.Entity.Raises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Repository
@Transactional
public interface RaisesRepository extends JpaRepository<Raises , Integer> {
    @Query("select u from Raises u Where u.emp_id =:x")
    List<Raises> getEmployeeRaisesByNationalId(@Param("x") int emp_id);


    @Query(nativeQuery = true, value = "Select *  from raises  where Month(raisesDate) = Month(:Date) " +
            "And Year(raisesDate) =  Year(:Date) and emp_id=:x")
    List<Raises> getRaisesByDateAndEmployeeId(@Param("Date") LocalDate toDate , @Param("x") int emp_id);
}
