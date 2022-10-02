package com.project.HR.Repostory;
import com.project.HR.Entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional
public interface LeaveRepository  extends JpaRepository<Leave, Integer> {

}
