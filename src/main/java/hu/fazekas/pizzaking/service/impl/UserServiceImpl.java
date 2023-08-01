package hu.fazekas.pizzaking.service.impl;

import hu.fazekas.pizzaking.dao.UserRepository;
import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.entity.User;
import hu.fazekas.pizzaking.exception.AlreadyExistsException;
import hu.fazekas.pizzaking.exception.NotFoundException;
import hu.fazekas.pizzaking.mapper.UserMapper;
import hu.fazekas.pizzaking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDto getUserById(Long id) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        return userMapper.entityToDto(user);
    }

    @Override
    public Long createUser(UserDto userDto) throws AlreadyExistsException {
        if (userRepository.existsUserByEmail(userDto.getEmail())) {
            throw new AlreadyExistsException("User with email already exists!");
        }

        return userRepository.save(userMapper.dtoToEntity(userDto)).getId();
    }

    @Override
    public UserDto modifyUser(Long id, UserDto userDto) throws AlreadyExistsException, NotFoundException {
        if (userRepository.existsUserByEmail(userDto.getEmail())) {
            throw new AlreadyExistsException("User with email already exists!");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());

        User savedUser = userRepository.save(user);

        return userMapper.entityToDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        userRepository.delete(user);
    }
}
