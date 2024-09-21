package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Optional<Role> findById(int id) {
        return roleDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Role role) {
        if (roleDao.findAll().stream().noneMatch((existingRole) -> role.getName().equals(existingRole.getName()))) {
            roleDao.save(role);
        }
    }
}
