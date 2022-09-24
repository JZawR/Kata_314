package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void updateUser(User user);
    List<User> getListUsers();
    User getUserById(long id);
    void deleteUserById(long id);
    User getUserByLogin(String login);
}
