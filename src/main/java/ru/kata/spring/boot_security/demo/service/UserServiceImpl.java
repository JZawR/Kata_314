package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositorie.RoleRepository;
import ru.kata.spring.boot_security.demo.repositorie.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void addUser(User newUser, Long[] idRoles) {
        newUser.setRoles(rolesControl(idRoles));
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.saveAndFlush(newUser);
    }

    @Transactional
    public void updateUser(User updatedUser, Long[] idRoles) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        updatedUser.setRoles(rolesControl(idRoles));
        userRepository.saveAndFlush(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public Set<Role> getRoles() {
        return Set.copyOf(roleRepository.findAll());
    }

    public Set<Role> rolesControl(Long[] idRoles) {
        Set<Role> roleSet = new HashSet<>();
        for (Long rID : idRoles) {
            if (rID == 1) {
                roleSet.add(new Role(1L,"USER"));
            } else if (rID == 2) {
                roleSet.add(new Role(2L, "ADMIN"));
            }
        }
        return roleSet;
    }
}
