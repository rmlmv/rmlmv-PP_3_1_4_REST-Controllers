package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.RoleDto;
import ru.kata.spring.boot_security.demo.mappers.RoleMapper;
import ru.kata.spring.boot_security.demo.services.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/roles")
public class RolesController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RolesController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping()
    public ResponseEntity<List<RoleDto>> findAll() {
        List<RoleDto> roleDtoList = roleService.findAll().stream()
                .map(roleMapper::convertToRoleDto).collect(Collectors.toList());

        return roleDtoList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(roleDtoList);
    }
}
