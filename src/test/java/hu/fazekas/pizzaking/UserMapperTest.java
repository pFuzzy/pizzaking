package hu.fazekas.pizzaking;

import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.entity.User;
import hu.fazekas.pizzaking.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void entityToDtoTest() {
        UserDto expectedUserDto = new UserDto().id(1L)
                .email("test@test.hu")
                .address("test address");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.hu");
        user.setAddress("test address");

        Assert.assertEquals(expectedUserDto, userMapper.entityToDto(user));

    }

    @Test
    public void dtoToEntityTest() {
        UserDto userDto = new UserDto().id(1L).email("test@test.hu").address("test address");

        User mappedUser = userMapper.dtoToEntity(userDto);

        Assert.assertEquals(mappedUser.getId(), userDto.getId());
        Assert.assertEquals(mappedUser.getEmail(), userDto.getEmail());
        Assert.assertEquals(mappedUser.getAddress(), userDto.getAddress());
    }
}
