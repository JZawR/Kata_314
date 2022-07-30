package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User getUser(Long id);
    List<User> getAllUsers();
    void addUser(User newUser, Long[] idRoles);
    void updateUser(User updatedUser, Long[] idRoles);
    void deleteUser(Long id);
    Set<Role> getRoles();
    Set<Role> rolesControl(Long[] idRoles);
}
