package ru.practicum.shareit.user.storage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Data
@RequiredArgsConstructor
@Repository
public class UserStorage implements UserStorageDao {
    private final HashMap<Integer, User> users = new HashMap<>();
    List<String> emailList = new ArrayList<>();
    int id = 1;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(int id) {
        return users.get(id);
    }

    @Override
    public User add(User user) {
        emailList.clear();
        for (User tool : users.values()) {
        //for (int i = 0; i<users.size(); i++) {
            emailList.add(tool.getEmail());
        }
        if(emailList.contains(user.getEmail())){
            throw new ValidationException("Пользователь с таким email уже существует");
        }
        if(user.getId() == 0) {
            user.setId(id);
            users.put(user.getId(),user);
        } else {
            users.put(user.getId(), user);
        }
        id++;
        return user;
    }

    @Override
    public User update(UserDto userDto, int userId) {
        if (users.values().stream()
                .map(User::getEmail)
                .anyMatch(U -> U.equals(userDto.getEmail()))) {
            throw new ValidationException("Пользователь с таким email уже существует!");
        }

        if (userDto.getEmail() != null) {
            users.get(userId).setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            users.get(userId).setName(userDto.getName());
        }

        return users.get(userId);
    }

    @Override
    public void deleteUser(int userId) {
        users.remove(userId);
    }
}
