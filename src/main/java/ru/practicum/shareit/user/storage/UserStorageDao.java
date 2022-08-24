package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorageDao {
    Collection<User> findAll();

    User findById(int id);

    User add(User user);

    User update(User user, int userId);

    void deleteUser(int userId);

}
