package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Role> getRoleList() {
		return entityManager.createQuery("from Role", Role.class).getResultList();
	}

	@Override
	public void saveRole(Role role) {
		entityManager.persist(role);
	}

	@Override
	public List<Role> getRolesById(List<Long> id) {
		return  entityManager.createQuery("FROM Role WHERE id IN :id", Role.class)
				.setParameter("id", id)
				.getResultList();
	}
}
