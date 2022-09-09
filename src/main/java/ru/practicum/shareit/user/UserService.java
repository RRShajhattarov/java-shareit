package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;
import ru.practicum.shareit.user.storage.UserStorageDao;

import java.util.Collection;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorageDao userStorage;
    public Collection<User> findAll() {
        log.info("Получение списка всех пользователей");
        return userStorage.findAll();
    }

    public User add(User user) throws ValidationException {
        return userStorage.add(user);
    }

    public User update(UserDto user, int userId) {

        return userStorage.update(user, userId);
    }

    public User findUserById(int userId) {
        return userStorage.findById(userId);
    }

    public void deleteUser(int userId) {
        userStorage.deleteUser(userId);
    }
}
