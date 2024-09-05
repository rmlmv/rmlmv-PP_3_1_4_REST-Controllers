package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);

    User findByEmail(String email);

    List<User> findAllWithRoles();

    void save(User user);

    void update(User user);

    void deleteById(Long id);
}
