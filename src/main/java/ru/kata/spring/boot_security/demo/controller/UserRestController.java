package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    public User showUserForUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/roles")
    public Set<Role> getAllRoles() {
        return userService.getRoles();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return  userService.getUser(id);
    }

    @PostMapping("/admin/create")
    public void createUser(@ModelAttribute User user, @RequestParam("roles") Long[] roles) {
        userService.addUser(user, roles);
    }

    @PatchMapping("/edit/{id}")
    public void updateUser(@ModelAttribute User user, @RequestParam("roles") Long[] roles) {
        userService.updateUser(user, roles);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
