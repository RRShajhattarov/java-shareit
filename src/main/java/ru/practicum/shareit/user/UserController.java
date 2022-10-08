package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.EmailNotExistsException;
import ru.practicum.shareit.user.dto.UserDto;


import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public UserDto add(@Validated(UserDto.Create.class) @RequestBody UserDto userDto, BindingResult results) {
        if (results.hasErrors()) {
            throw new EmailNotExistsException("error");
        }
        return userService.add(userDto);
    }

    @PatchMapping("/{id}")
    public UserDto update(@Validated(UserDto.Update.class) @RequestBody UserDto user,
                          @PathVariable("id") Long userId, BindingResult results) {
        if (results.hasErrors()) {
            throw new EmailNotExistsException("error");
        }
        return userService.update(user, userId);
    }

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable("id") Long userId) {
        return userService.findUserById(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }

}