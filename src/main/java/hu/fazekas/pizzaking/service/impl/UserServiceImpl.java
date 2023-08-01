package hu.fazekas.pizzaking.service.impl;

import hu.fazekas.pizzaking.dao.UserRepository;
import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.entity.User;
import hu.fazekas.pizzaking.exception.AlreadyExistsException;
import hu.fazekas.pizzaking.exception.NotFoundException;
import hu.fazekas.pizzaking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto getUserById(Long id) throws NotFoundException
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        return new UserDto()
                .id(user.getId())
                .email(user.getEmail())
                .address(user.getAddress());
    }

    @Override
    public Long createUser(UserDto userDto) throws AlreadyExistsException {
        if (userRepository.existsUserByEmail(userDto.getEmail())) {
            throw new AlreadyExistsException("User with email already exists!");
        }
        User userEntity = new User();

        userEntity.setEmail(userDto.getEmail());
        userEntity.setAddress(userDto.getAddress());

        return userRepository.save(userEntity).getId();
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

        userRepository.save(user);

        return userDto;
    }

    @Override
    public void deleteUser(Long id) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        userRepository.delete(user);
    }
}
