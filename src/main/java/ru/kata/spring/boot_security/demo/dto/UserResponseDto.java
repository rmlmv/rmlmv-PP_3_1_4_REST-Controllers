package ru.kata.spring.boot_security.demo.dto;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class UserResponseDto {
    private UserDto userDto;
    private Map<String, String> validationErrors;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public void setValidationErrors(BindingResult bindingResult) {
        if (validationErrors == null) {
            validationErrors = new HashMap<>();
        }
        bindingResult.getFieldErrors()
                .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));
    }
}
