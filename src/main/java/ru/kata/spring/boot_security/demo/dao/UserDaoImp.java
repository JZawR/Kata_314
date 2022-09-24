package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public List<User> getListUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void deleteUserById(long userId) {
        entityManager.remove(getUserById(userId));
    }

    @Override
    public User getUserById(long id) {
        return entityManager.createQuery("FROM User WHERE id =:id", User.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public User getUserByLogin(String email) {
        return  entityManager.createQuery("FROM User u JOIN FETCH u.roles roles where u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
