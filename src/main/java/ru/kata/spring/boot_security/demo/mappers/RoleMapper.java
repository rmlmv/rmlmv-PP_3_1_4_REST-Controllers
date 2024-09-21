package ru.kata.spring.boot_security.demo.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.RoleDto;
import ru.kata.spring.boot_security.demo.models.Role;

@Component
public class RoleMapper {
    private final ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role convertToRole(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    public RoleDto convertToRoleDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }
}
