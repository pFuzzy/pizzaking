package hu.fazekas.pizzaking.service;

import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.exception.AlreadyExistsException;
import hu.fazekas.pizzaking.exception.NotFoundException;

public interface UserService {

    UserDto getUserById(Long id) throws NotFoundException;

    Long createUser(UserDto user) throws AlreadyExistsException;

    UserDto modifyUser(Long id, UserDto user) throws NotFoundException, AlreadyExistsException;

    void deleteUser(Long id) throws NotFoundException;
}
