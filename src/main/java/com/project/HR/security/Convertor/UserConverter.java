package com.project.HR.security.Convertor;
import com.project.HR.security.Command.UserCommand;
import com.project.HR.security.Dto.UserDto;
import com.project.HR.security.Entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class UserConverter {
    @Autowired
    ModelMapper mapper;
    public User convertCommandToEntity(UserCommand userCommand) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        User user = mapper.map(userCommand,User.class);
        return user;
    }
    public UserDto convertEntityToDto(User user) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }
}
