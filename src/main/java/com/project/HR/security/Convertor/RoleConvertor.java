package com.project.HR.security.Convertor;
import com.project.HR.security.Command.RoleCommand;
import com.project.HR.security.Dto.RoleDto;
import com.project.HR.security.Entity.Role;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class RoleConvertor {
    @Autowired
    ModelMapper mapper;
    public Role convertCommandToEntity(RoleCommand roleCommand) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
     Role role = mapper.map(roleCommand,Role.class);
     return role;
    }
    public RoleDto convertEntityToDto(Role role) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        RoleDto roleDto = mapper.map(role, RoleDto.class);
        return roleDto;
    }
}
