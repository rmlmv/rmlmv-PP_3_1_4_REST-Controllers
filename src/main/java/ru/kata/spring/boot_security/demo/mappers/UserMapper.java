package ru.kata.spring.boot_security.demo.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    public UserMapper(ModelMapper modelMapper, RoleService roleService) {
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    public User convertToUser(UserDto userDto) {
        userDto.getRoles().forEach(role -> role.setName(roleService.findById(role.getId()).get().getName()));
        return modelMapper.map(userDto, User.class);
    }

    public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
