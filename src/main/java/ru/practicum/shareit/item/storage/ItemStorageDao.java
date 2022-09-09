package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;

public interface ItemStorageDao {


    Item findById(int id, int userId);

    Item add(User user, ItemDto item);

    Item update(Item item, int itemId, int userId);

    void deleteUser(int itemId);

    List<Item> findAllItems(int userId);

    List<Item> search(String text);
}
