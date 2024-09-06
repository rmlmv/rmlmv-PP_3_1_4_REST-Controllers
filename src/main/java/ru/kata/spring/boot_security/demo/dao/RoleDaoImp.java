package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImp implements RoleDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Role> findAll() {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r", Role.class);
        return query.getResultList();
    }

    @Override
    public Optional<Role> findById(int id) {
        return Optional.ofNullable(entityManager.find(Role.class, id));
    }
}
