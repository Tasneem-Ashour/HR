package com.project.HR.security.Service;
import com.project.HR.Repostory.EmployeeRepository;
import com.project.HR.security.Command.UserCommand;
import com.project.HR.security.Convertor.RoleConvertor;
import com.project.HR.security.Convertor.UserConverter;
import com.project.HR.security.Dto.RoleDto;
import com.project.HR.security.Dto.UserDto;
import com.project.HR.security.Entity.User;
import com.project.HR.security.Reopsitory.RoleRepository;
import com.project.HR.security.Reopsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserConverter userConverter;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleConvertor roleConvertor;

    @Autowired
    RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       User user =userRepository.findByEmail(email);
       if(user ==null){
           throw  new UsernameNotFoundException("user not found");
       }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
       user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));});
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    public UserDto addUser(UserCommand userCommand) throws Exception {
        if (employeeRepository.findById(userCommand.getEmployee_id()).isEmpty()) {
            throw new Exception("employee doesn't exist");
        }
        var roles = roleRepository.findAll();
        User user = userConverter.convertCommandToEntity(userCommand);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserDto userDto = userConverter.convertEntityToDto(user);
        List<RoleDto> list = new ArrayList<>();
        roles.forEach(e->list.add(roleConvertor.convertEntityToDto(e)));
        userDto.setRoleDto(list);
        return userDto;
    }
    public UserDto getUser(int id) {

        User user = userRepository.findById(id).get();
        UserDto userDto = userConverter.convertEntityToDto(user);
        return userDto;
    }
}
