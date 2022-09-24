package ru.kata.spring.boot_security.demo.dataloader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {
    private final UserService userService;
    private final RoleDao roleDao;

    public DataInitializer(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @Transactional
    public void run(ApplicationArguments args) {
        User user = new User();
        User q = new User();
        User admin = new User();
        User admin1 = new User();
        roleDao.saveRole(new Role("USER"));
        roleDao.saveRole(new Role("ADMIN"));

        user.setEmail("user@mail.ru");
        user.setPassword("user");
        user.setName("Ivan");
        user.setSurname("Ivanov");
        user.setAge(11);
        userService.addUser(user, List.of(2L,1L));

//        q.setEmail("q");
//        q.setPassword("q");
//        q.setName("Pavel");
//        q.setSurname("Pavlov");
//        q.setAge(22);
//        userService.addUser(q, new Long[]{2L});
//
//        admin.setEmail("admin@mail.ru");
//        admin.setPassword("admin");
//        admin.setName("Nikolay");
//        admin.setSurname("Nikolaev");
//        admin.setAge(33);
//        userService.addUser(admin, new Long[]{2L});
//
//        admin1.setEmail("admin1@mail.ru");
//        admin1.setPassword("admin1");
//        admin1.setName("Andrey");
//        admin1.setSurname("Andreev");
//        admin1.setAge(44);
//        userService.addUser(admin1, new Long[]{1L, 2L});
    }
}
