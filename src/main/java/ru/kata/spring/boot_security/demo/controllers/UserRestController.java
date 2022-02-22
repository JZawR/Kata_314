package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;
    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @GetMapping("/admin")
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User showUserForUser(@PathVariable Long id) {
        return  userService.getUser(id);
    }

    @PostMapping("/admin/create")
    public void createUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.addUser(user);
    }

    @PatchMapping("/edit/{id}")
    public void updateUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.updateUser(user);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
