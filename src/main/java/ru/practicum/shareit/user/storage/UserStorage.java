package ru.practicum.shareit.user.storage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.ValidationException;
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
            user.setId(users.size() + 1);
            users.put(user.getId(),user);
        } else {
            users.put(user.getId(), user);
        }

        return user;
    }

    @Override
    public User update(User user, int userId) {
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public void deleteUser(int userId) {
        users.remove(userId);
    }
}
