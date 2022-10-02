package com.project.HR.Repostory;
import com.project.HR.Entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Integer> {
}
