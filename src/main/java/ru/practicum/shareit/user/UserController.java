package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import javax.validation.Valid;
import java.util.Collection;
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
    public List<UserDto> findAll() {return userService.findAll();}

    @PostMapping
    public UserDto add(@Valid @RequestBody User user) {
        return userService.add(user);
    }

    @PatchMapping("/{id}")
    public UserDto update(@Valid @RequestBody UserDto user,
                        @PathVariable("id") int userId ) {return userService.update(user,userId);}

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable("id") int userId) {return userService.findUserById(userId);}

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int userId) {
        userService.deleteUser(userId);
    }

}
