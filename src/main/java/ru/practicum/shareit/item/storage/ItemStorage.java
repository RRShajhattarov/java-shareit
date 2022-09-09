package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.UserIdNotValidation;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.*;


@Component
public class ItemStorage implements ItemStorageDao {

    private final HashMap<Integer, Item> items = new HashMap<>();
    int id =1;
    @Override
    public List<Item> findAllItems(int userId) {
        List<Item> userItems = new ArrayList<>();
        for (int i : items.keySet()) {
            if (items.get(i).getOwner().getId() == userId) {
                userItems.add(items.get(i));
            }
        }
        return userItems;
    }

    @Override
    public List<Item> search(String text) {
        List<Item> searchItems = new ArrayList<>();

        if (Objects.equals(text, "") || text == null) {
            return searchItems;
        }

        for (int i : items.keySet()) {
            if (items.get(i).getName().toLowerCase().contains(text.toLowerCase()) ||
                    items.get(i).getDescription().toLowerCase().contains(text.toLowerCase()) &&
                            items.get(i).getAvailable()) {
                searchItems.add(items.get(i));
            }
        }
        return searchItems;
    }

    @Override
    public Item findById(int itemId, int userId) {
        if (items.get(itemId).getOwner().getId() != userId) {
            throw new UserIdNotValidation("Вы не являетесь владельцем этого продукта");
        }
        return items.get(itemId);
    }

    @Override
    public Item add(User user, ItemDto item) {
        items.put(id, ItemMapper.toItem(item, id, user));
        id++;
        return items.get(id-1);
    }

    @Override
    public Item update(Item item, int itemId, int userId) {

        if (items.get(itemId).getOwner().getId() != userId) {
            throw new UserIdNotValidation("Вы не являетесь владельцем этого продукта");
        }
        if (item.getDescription() != null) {
            items.get(itemId).setDescription(item.getDescription());
        }
        if (item.getName() != null) {
            items.get(itemId).setName(item.getName());
        }
        if (item.getAvailable() != null) {
            items.get(itemId).setAvailable(item.getAvailable());
        }
        return items.get(itemId);
    }

    @Override
    public void deleteUser(int itemId) {

    }
}
