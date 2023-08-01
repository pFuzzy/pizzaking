package hu.fazekas.pizzaking.controller;

import hu.fazekas.pizzaking.dto.UserDto;
import hu.fazekas.pizzaking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user/{id}")
    public UserDto getUserById(@PathVariable Long id) throws Exception {
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public Long createUser(@RequestBody UserDto user) throws Exception {
        return userService.createUser(user);
    }

    @PutMapping("/user/{id}")
    public UserDto modifyUser(@PathVariable Long id, @RequestBody UserDto user) throws Exception {
        return userService.modifyUser(id, user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
