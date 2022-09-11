package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.UserIdNotValidation;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.*;



@Component
public class ItemStorage implements ItemStorageDao {

    private final HashMap<Integer, Item> items = new HashMap<>();
    private int id =1;
    @Override
    public List<ItemDto> findAllItems(int userId) {
        List<ItemDto> userItem = new ArrayList<>();

        for (int i : items.keySet()) {
            if (items.get(i).getOwner().getId() == userId) {
                userItem.add(ItemMapper.toItemDto(items.get(i)));
            }
        }
        return userItem;
    }

    @Override
    public List<ItemDto> search(String text) {
        List<ItemDto> searchItems = new ArrayList<>();

        if (Objects.equals(text, "") || text == null) {
            return searchItems;
        }

        for (int i : items.keySet()) {
            if (items.get(i).getName().toLowerCase().contains(text.toLowerCase()) ||
                    items.get(i).getDescription().toLowerCase().contains(text.toLowerCase()) &&
                            items.get(i).getAvailable()) {
                searchItems.add(ItemMapper.toItemDto(items.get(i)));
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
    public Item add(User user, ItemDto itemDto) {
        items.put(id, ItemMapper.toItem(itemDto, id, user));
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
}
