package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.EmptyNameItemException;
import ru.practicum.shareit.item.exception.NotAvailableException;
import ru.practicum.shareit.item.exception.UserIdNotValidation;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorageDao;
import ru.practicum.shareit.user.storage.UserStorageDao;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemStorageDao itemStorageDao;
    private final UserStorageDao userStorageDao;

    public ItemDto add(int userId, ItemDto itemDto) {

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

        return ItemMapper.toItemDto(itemStorageDao.add(userStorageDao.findById(userId), itemDto));

    }

    public ItemDto update(Integer itemId, int userId, Item item) {
        if (userStorageDao.findById(userId) == null) {
            throw new UserIdNotValidation("Пользователь с id " + userId + " не найден.");
        }

        return ItemMapper.toItemDto(itemStorageDao.update(item, itemId, userId));
    }

    public ItemDto findItemById(Integer itemId, int userId) {
        if (userStorageDao.findById(userId) == null) {
            throw new UserIdNotValidation("Пользователь с id " + userId + " не найден.");
        }
        return ItemMapper.toItemDto(itemStorageDao.findById(itemId, userId));
    }

    public List<ItemDto> findAllItems(int userId) {
        return itemStorageDao.findAllItems(userId);
    }

    public List<ItemDto> search(String text) {
        return itemStorageDao.search(text);
    }
}
