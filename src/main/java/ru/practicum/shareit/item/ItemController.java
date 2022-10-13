package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto add(@RequestHeader(value="X-Sharer-User-Id") Long userId,
    @Valid @RequestBody ItemDto itemDto) {
        return itemService.add(userId,itemDto);
    }

    @PatchMapping("{itemId}")
    public ItemDto update(@PathVariable Long itemId,
                       @RequestHeader(value="X-Sharer-User-Id") Long userId,
                       @RequestBody ItemDto itemDto) {
        return itemService.update(itemId, userId, itemDto);
    }

    @GetMapping("{itemId}")
    public ItemDto findById(@PathVariable Long itemId,
                                   @RequestHeader(value="X-Sharer-User-Id") Long userId) {
        return itemService.findItemById(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> findAllItems(@RequestHeader(value="X-Sharer-User-Id") Long userId) {
        return itemService.findAllItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.search(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto setComment(@PathVariable Long itemId,
                                 @RequestBody CommentDto commentDto,
                                 @RequestHeader("X-Sharer-User-Id") Long authorId) {
        return itemService.addComment(itemId, commentDto, authorId);
    }

}
