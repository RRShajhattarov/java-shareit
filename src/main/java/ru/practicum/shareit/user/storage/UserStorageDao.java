package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorageDao {
    List<UserDto> findAll();

    User findById(int id);

    User add(User user);

    User update(UserDto user, int userId);

    void deleteUser(int userId);

}
