package hu.fazekas.pizzaking;

import hu.fazekas.pizzaking.dao.UserRepository;
import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.entity.User;
import hu.fazekas.pizzaking.exception.AlreadyExistsException;
import hu.fazekas.pizzaking.exception.NotFoundException;
import hu.fazekas.pizzaking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;


    @Test
    public void getUserByIdTest_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(1L));

        verify(userRepository, times(1))
                .findById(1L);
    }

    @Test
    public void getUserByIdTest_success() throws NotFoundException {
        UserDto expectedUserDto = new UserDto().id(1L).email("test@test.hu").address("test address");

        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        assertEquals(expectedUserDto, userService.getUserById(expectedUserDto.getId()));

        verify(userRepository, times(1))
                .findById(expectedUserDto.getId());
    }

    @Test
    public void createUserTest_emailAlreadyExists() {
        UserDto givenUserDto = new UserDto().email("test@test.hu").address("test address");

        when(userRepository.existsUserByEmail(givenUserDto.getEmail())).thenReturn(true);

        assertThrows(AlreadyExistsException.class,
                () -> userService.createUser(givenUserDto));

        verify(userRepository, times(1))
                .existsUserByEmail(givenUserDto.getEmail());

    }

    @Test
    public void createUserTest_success() throws AlreadyExistsException {
        UserDto givenUserDto = new UserDto().email("test@test.hu").address("test address");

        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        when(userRepository.existsUserByEmail(givenUserDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        assertEquals(Optional.of(1L), Optional.ofNullable(userService.createUser(givenUserDto)));

        verify(userRepository, times(1))
                .existsUserByEmail(givenUserDto.getEmail());
        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    public void modifyUserTest_emailAlreadyExists(){
        UserDto givenUserDto = new UserDto().id(1L).email("test@test.hu").address("test address");

        when(userRepository.existsUserByEmail(givenUserDto.getEmail())).thenReturn(true);

        assertThrows(AlreadyExistsException.class,
                () -> userService.modifyUser(1L, givenUserDto));

        verify(userRepository, times(1))
                .existsUserByEmail(givenUserDto.getEmail());
    }

    @Test
    public void modifyUserTest_userNotFound(){
        UserDto givenUserDto = new UserDto().id(1L).email("test@test.hu").address("test address");

        when(userRepository.existsUserByEmail(givenUserDto.getEmail())).thenReturn(false);
        when(userRepository.findById(givenUserDto.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.modifyUser(givenUserDto.getId(), givenUserDto));

        verify(userRepository, times(1))
                .existsUserByEmail(givenUserDto.getEmail());
        verify(userRepository, times(1))
                .findById(givenUserDto.getId());
    }

    @Test
    public void modifyUserTest_success() throws AlreadyExistsException, NotFoundException {
        UserDto givenUserDto = new UserDto().id(1L).email("test@test.hu").address("test address");

        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        when(userRepository.existsUserByEmail(givenUserDto.getEmail())).thenReturn(false);
        when(userRepository.findById(givenUserDto.getId())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        assertEquals(givenUserDto, userService.modifyUser(givenUserDto.getId(), givenUserDto));

        verify(userRepository, times(1))
                .existsUserByEmail(givenUserDto.getEmail());
        verify(userRepository, times(1))
                .findById(givenUserDto.getId());
        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    public void deleteUserTest_userNotFound(){
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository, times(1))
                .findById(1L);
    }

    @Test
    public void deleteUserTest_success() throws NotFoundException {
        Long userId = 1L;

        User userEntity = new User();
        userEntity.setId(userId);
        userEntity.setEmail("test@test.hu");
        userEntity.setAddress("test address");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        userService.deleteUser(userEntity.getId());

        verify(userRepository, times(1))
                .findById(1L);
        verify(userRepository, times(1))
                .delete(userEntity);
    }
}
