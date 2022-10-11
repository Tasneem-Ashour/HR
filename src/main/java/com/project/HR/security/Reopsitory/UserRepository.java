package com.project.HR.security.Reopsitory;
import com.project.HR.security.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);

}
