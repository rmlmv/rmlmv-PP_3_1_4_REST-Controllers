package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String getAdminPanel(@ModelAttribute(name = "user") User user,
                                Model model) {

        model.addAttribute("users", userService.findAllWithRoles());
        model.addAttribute("roles", roleService.findAll());
        return "admin";
    }

    @GetMapping("/add-user")
    public String getAddUserPage(@ModelAttribute(name = "user") User user,
                                 Model model) {

        model.addAttribute("roles", roleService.findAll());
        return "add-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute(name = "user") @Valid User user,
                           BindingResult bindingResult, Model model) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            return "add-user";
        }

        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute(name = "user") @Valid User user,
                             BindingResult bindingResult, Model model) {

        if (!user.getEmail().equals(userService.findById(user.getId()).get().getEmail())) {
            userValidator.validate(user, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAllWithRoles());
            model.addAttribute("roles", roleService.findAll());
            return "admin";
        }

        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/remove-user")
    public String removeUser(@ModelAttribute(name = "user") User user) {
        userService.deleteById(user.getId());
        return "redirect:/admin";
    }
}
