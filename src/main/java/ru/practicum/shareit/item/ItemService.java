package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.EmptyNameItemException;
import ru.practicum.shareit.item.exception.NotAvailableException;
import ru.practicum.shareit.item.exception.UserIdNotValidation;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorageDao;
import ru.practicum.shareit.user.ValidationException;
import ru.practicum.shareit.user.storage.UserStorageDao;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemStorageDao itemStorageDao;
    private final UserStorageDao userStorageDao;

    public Item add(int userId, ItemDto itemDto) {
        if (userStorageDao.findById(userId) == null) {
            throw new UserIdNotValidation("Пользователь с id " + userId + " не найден.");
        }

        if (Objects.equals(itemDto.getName(), "") || itemDto.getName() == null ||
                itemDto.getDescription() == null || Objects.equals(itemDto.getDescription(), "")) {
            throw new EmptyNameItemException("Не указано название предмета.");
        }

        if (itemDto.getAvailable() == null) {
            throw new NotAvailableException("Укажите доступность предмета.");
        }
        return itemStorageDao.add(userStorageDao.findById(userId), itemDto);

    }

    public Item update(Integer itemId, int userId, Item item) {
        if (userStorageDao.findById(userId) == null) {
            throw new UserIdNotValidation("Пользователь с id " + userId + " не найден.");
        }

        return itemStorageDao.update(item, itemId, userId);
    }

    public Item findItemById(Integer itemId, int userId) {
        if (userStorageDao.findById(userId) == null) {
            throw new UserIdNotValidation("Пользователь с id " + userId + " не найден.");
        }
        return itemStorageDao.findById(itemId, userId);
    }

    public List<Item> findAllItems(int userId) {
        return itemStorageDao.findAllItems(userId);
    }

    public List<Item> search(String text) {
        return itemStorageDao.search(text);
    }
}
