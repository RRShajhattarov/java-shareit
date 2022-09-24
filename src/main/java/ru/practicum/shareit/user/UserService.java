package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;
import ru.practicum.shareit.user.storage.UserStorageDao;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorageDao userStorage;
    public List<UserDto> findAll() {
        log.info("Получение списка всех пользователей");
        return userStorage.findAll();
    }

    public UserDto add(User user) throws ValidationException {
        return UserMapper.toUserDto(userStorage.add(user));
    }

    public UserDto update(UserDto user, int userId) {

        return UserMapper.toUserDto(userStorage.update(user, userId));
    }

    public UserDto findUserById(int userId) {
        return UserMapper.toUserDto(userStorage.findById(userId));
    }

    public void deleteUser(int userId) {
        userStorage.deleteUser(userId);
    }
}
