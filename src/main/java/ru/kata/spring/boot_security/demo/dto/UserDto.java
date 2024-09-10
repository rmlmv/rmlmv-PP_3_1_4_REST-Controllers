package ru.kata.spring.boot_security.demo.dto;

import javax.validation.constraints.*;
import java.util.Set;

public class UserDto {
    private Long id;

    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String firstName;

    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String lastName;

    @Min(value = 0, message = "Age should be greater than 0")
    @Max(value = 120, message = "Age should be less than 200")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 2, message = "Password should be at least 2 characters")
    private String password;

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    //    @JsonManagedReference
    @Size(min = 1, message = "At least one role should be selected")
    private Set<RoleDto> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
