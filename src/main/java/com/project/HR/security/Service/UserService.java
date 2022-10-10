package com.project.HR.security.Service;
import com.project.HR.security.Command.UserCommand;
import com.project.HR.security.Convertor.UserConverter;
import com.project.HR.security.Dto.UserDto;
import com.project.HR.security.Entity.User;
import com.project.HR.security.Reopsitory.RoleRepository;
import com.project.HR.security.Reopsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserConverter userConverter;
    @Autowired
    RoleRepository roleRepository;
    public UserDto addUser(UserCommand userCommand) throws Exception {
        if(userCommand.getEmployee_id() == null)
            throw new Exception("employee doesn't exist");
        User user = userConverter.convertCommandToEntity(userCommand);
        user.setRoles(userCommand.getRoles());
        userRepository.save(user);
        UserDto userDto = userConverter.convertEntityToDto(user);
        return userDto;
    }
    public UserDto getUser(int id) {
        User user=    userRepository.findByEmployee_id( id);

//    User user=    userRepository.findById( id).get();
    UserDto userDto = userConverter.convertEntityToDto(user);
    return userDto;
    }
}
