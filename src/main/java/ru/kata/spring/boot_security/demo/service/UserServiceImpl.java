package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final RoleDao roleDao;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDao userDao, RoleDao roleDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    public User getUser(Long id) {
        return userDao.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getListUsers();
    }

    @Transactional
    public void addUser(User newUser, List<Long> idRoles) {
        newUser.setRoles(Set.copyOf(roleDao.getRolesById(idRoles)));
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userDao.addUser(newUser);
    }

    @Transactional
    public void updateUser(User updatedUser, List<Long> idRoles) {
        if(!updatedUser.getPassword().equals(userDao.getUserById(updatedUser.getId()).getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        updatedUser.setRoles(Set.copyOf(roleDao.getRolesById(idRoles)));
        userDao.updateUser(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.getUserByLogin(email);
    }

    public Set<Role> getRoles() {
        return Set.copyOf(roleDao.getRoleList());
    }
}
