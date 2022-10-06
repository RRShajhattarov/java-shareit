package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.exception.UserIdNotValidation;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User u : users ) {
            userDtos.add(UserMapper.toUserDto(u));
        }
        return userDtos;
    }

    public UserDto add(UserDto userDto) throws ValidationException {
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    public UserDto update(UserDto userDto, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotValidation("Пользователь с id " + userId + " не найден."));
        User user = UserMapper.toUser(userDto);
        user.setId(userId);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    public UserDto findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotValidation("Пользователь с id " + userId + " не найден."));

        return UserMapper.toUserDto(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
