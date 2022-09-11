package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto add(@RequestHeader(value="X-Sharer-User-Id") int userId,
    @Valid @RequestBody ItemDto itemDto) {
        return itemService.add(userId,itemDto);
    }

    @PatchMapping("{itemId}")
    public ItemDto update(@PathVariable Integer itemId,
                       @RequestHeader(value="X-Sharer-User-Id") int userId,
                       @RequestBody Item item) {
        return itemService.update(itemId, userId, item);
    }

    @GetMapping("{itemId}")
    public ItemDto findById(@PathVariable Integer itemId,
                         @RequestHeader(value="X-Sharer-User-Id") int userId) {
        return itemService.findItemById(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> findAllItems(@RequestHeader(value="X-Sharer-User-Id") int userId) {
        return itemService.findAllItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.search(text);
    }

}
