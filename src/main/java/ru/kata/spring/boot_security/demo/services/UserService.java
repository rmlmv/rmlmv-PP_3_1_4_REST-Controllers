package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    User findByEmail(String email);

    List<User> findAllWithRoles();

    @Transactional
    void save(User user);

    @Transactional
    void update(User user);

    @Transactional
    void deleteById(Long id);

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
