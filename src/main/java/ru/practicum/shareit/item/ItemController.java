package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item add(@RequestHeader(value="X-Sharer-User-Id") int userId,
    @Valid @RequestBody ItemDto item) {
        return itemService.add(userId,item);
    }

    @PatchMapping("{itemId}")
    public Item update(@PathVariable Integer itemId,
                       @RequestHeader(value="X-Sharer-User-Id") int userId,
                       @RequestBody Item item) {
        return itemService.update(itemId, userId, item);
    }

    @GetMapping("{itemId}")
    public Item findById(@PathVariable Integer itemId,
                         @RequestHeader(value="X-Sharer-User-Id") int userId) {
        return itemService.findItemById(itemId, userId);
    }

    @GetMapping
    public List<Item> findAllItems(@RequestHeader(value="X-Sharer-User-Id") int userId) {
        return itemService.findAllItems(userId);
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam String text) {
        return itemService.search(text);
    }

}
