package com.project.HR.Repostory;
import com.project.HR.Entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Integer> {

    @Query(nativeQuery = true, value = "Select *  from bonus  where Month(bonusDate) = Month(:Date) " +
            "And Year(bonusDate) =  Year(:Date) and emp_id=:x")
    List<Bonus> getBonusByDateAndEmployeeId(@Param("Date") LocalDate toDate , @Param("x") int emp_id);
}
