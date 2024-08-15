package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority, Comparable<Role> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public void addUserToRole(User user) {
        if (null == users) {
            users = new TreeSet<>();
        }
        users.add(user);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Role other) {
        return this.id - other.id;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
