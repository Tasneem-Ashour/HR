package com.project.HR.security.Service;
import com.project.HR.security.Command.RoleCommand;
import com.project.HR.security.Convertor.RoleConvertor;
import com.project.HR.security.Dto.RoleDto;
import com.project.HR.security.Entity.Role;
import com.project.HR.security.Reopsitory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleConvertor roleConvertor;

    public RoleDto addRole(RoleCommand roleCommand){
        Role role = roleConvertor.convertCommandToEntity(roleCommand);
        roleRepository.save(role);
        RoleDto roleDto = roleConvertor.convertEntityToDto(role);
        return roleDto;
    }

    public List<RoleDto> getAllRecord() {
       List<Role>  roles=  roleRepository.findAll();
        List<RoleDto> list = new ArrayList<>();
        roles.forEach(e->list.add(roleConvertor.convertEntityToDto(e)));
        return list;
    }
}
