package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.dto.UserResponseDto;
import ru.kata.spring.boot_security.demo.mappers.UserMapper;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    public UsersController(UserService userService, UserValidator userValidator, UserMapper userMapper) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> findAllWithRoles() {
        List<UserDto> userDtoList = userService.findAllWithRoles().stream()
                .map(userMapper::convertToUserDto).collect(Collectors.toList());

        return userDtoList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> findBySelf(Principal principal) {
        return ResponseEntity.ok(userMapper.convertToUserDto(userService.findByEmail(principal.getName())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
        Optional<User> optionalUser = userService.findById(id);

        return optionalUser.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(userMapper.convertToUserDto(optionalUser.get()));
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> save(@RequestBody @Valid UserDto userDto,
                                                BindingResult bindingResult) {

        UserResponseDto responseDto = new UserResponseDto();
        User user = userMapper.convertToUser(userDto);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            responseDto.setUserDto(userDto);
            responseDto.setValidationErrors(bindingResult);

            return ResponseEntity.badRequest().body(responseDto);
        }

        userService.save(user);

        responseDto.setUserDto(userMapper.convertToUserDto(user));

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping()
    public ResponseEntity<UserResponseDto> update(@RequestBody @Valid UserDto userDto,
                                                  BindingResult bindingResult) {

        UserResponseDto responseDto = new UserResponseDto();
        User user = userMapper.convertToUser(userDto);

        if (!user.getEmail().equals(userService.findById(user.getId()).get().getEmail())) {
            userValidator.validate(user, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            responseDto.setUserDto(userDto);
            responseDto.setValidationErrors(bindingResult);

            return ResponseEntity.badRequest().body(responseDto);
        }

        userService.update(user);

        responseDto.setUserDto(userMapper.convertToUserDto(user));

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
