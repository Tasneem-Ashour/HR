package com.project.HR.security.Reopsitory;
import com.project.HR.security.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {


}
