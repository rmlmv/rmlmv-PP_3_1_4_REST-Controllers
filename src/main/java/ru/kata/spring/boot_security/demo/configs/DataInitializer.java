package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Set;
import java.util.TreeSet;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;

    public DataInitializer(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initTestUsers() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleService.save(roleAdmin);
        roleService.save(roleUser);

        User user1 = new User("Admin", "User1", 0, "a@a.com");
        user1.setPassword("1");
        user1.setRoles(new TreeSet<>(Set.of(roleAdmin)));

        User user2 = new User("User", "User2", 1, "u@u.com");
        user2.setPassword("1");
        user2.setRoles(new TreeSet<>(Set.of(roleUser)));

        User user3 = new User("AdminUser", "User3", 10, "a@u.com");
        user3.setPassword("1");
        user3.setRoles(new TreeSet<>(Set.of(roleAdmin, roleUser)));

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
    }
}
