package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.services.RoleService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RoleService roleService;

    public AdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAdminPanel(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "admin";
    }

    @GetMapping("/add-user")
    public String getAddUserPage(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "add-user";
    }
}
