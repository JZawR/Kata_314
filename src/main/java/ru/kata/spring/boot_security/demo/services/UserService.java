package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User newUser, Long[] idRoles) {
        newUser.setRoles(rolesControl(idRoles));
        userRepository.saveAndFlush(newUser);
    }

    public void updateUser(User updatedUser, Long[] idRoles) {
        updatedUser.setRoles(rolesControl(idRoles));
        userRepository.saveAndFlush(updatedUser);
    }

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
