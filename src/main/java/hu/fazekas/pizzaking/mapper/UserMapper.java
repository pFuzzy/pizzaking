package hu.fazekas.pizzaking.mapper;

import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto entityToDto(User user){
        return new UserDto().id(user.getId()).email(user.getEmail()).address(user.getAddress());
    }

    public User dtoToEntity(UserDto userDto){
        User user = new User();

        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());

        return user;
    }
}
