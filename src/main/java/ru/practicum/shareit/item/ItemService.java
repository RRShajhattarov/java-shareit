package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.enums.BookingStatus;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.exception.EmptyNameItemException;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.UserIdNotValidation;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;

    public ItemDto add(Long userId, ItemDto itemDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotValidation("Пользователь с id " + userId + " не найден."));

        if (Objects.equals(itemDto.getName(), "") || itemDto.getName() == null ||
                itemDto.getDescription() == null || Objects.equals(itemDto.getDescription(), "")) {
            throw new EmptyNameItemException("Не указано название предмета.");
        }

        if (itemDto.getAvailable() == null) {
            throw new NotAvailableException("Укажите доступность предмета.");
        }
        itemDto.setOwner(user);
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemRepository.save(item));

    }

    public ItemDto update(Long itemId, Long userId, ItemDto itemDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotValidation("Пользователь с id " + userId + " не найден."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Продукт с id " + itemId + " не найден."));
        if(!item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Вы не являетесь владельцем продукта.");
        }

        itemDto.setId(itemId);
        patchUpdate(item,itemDto);
          return ItemMapper.toItemDto(itemRepository.save(item));
    }

    private void patchUpdate(Item item, ItemDto itemDto) {
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getOwner() != null) {
            item.setOwner(itemDto.getOwner());
        }
    }

    public ItemDto findItemById(Long itemId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotValidation("Пользователь с id " + userId + " не найден."));

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new EmptyNameItemException("Продукт с id " + itemId + " не найден!"));
        if(!item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Вы не являетесь владельцем продукта.");
        }
        return ItemMapper.toItemDto(item);
    }

    public List<ItemDto> findAllItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotValidation("Пользователь с id " + userId + " не найден."));

        List<Item> items = itemRepository.findAllByOwner(user);
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item i : items) {
            itemDtos.add(ItemMapper.toItemDto(i));
        }
        return itemDtos;
    }

    public List<ItemDto> search(String text) {
         List<Item> items = itemRepository.search(text);
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item i : items) {
            itemDtos.add(ItemMapper.toItemDto(i));
        } 
        return itemDtos;
    }

    public CommentDto addComment(Long itemId, CommentDto commentDto, Long authorId) {
        if (commentDto == null || commentDto.getText() == null || commentDto.getText().equals("")) {
            throw new NullPointerException("Комментарий не может быть пустым.");
        }

        List<Booking> booking = bookingRepository.getBookingByItemIdAndUserId(itemId, authorId)
                .orElseThrow(() -> new NotFoundException("Booking doesn't exists"));


        booking = booking.stream()
                .filter(s -> s.getStatus().equals(BookingStatus.APPROVED))
                .filter(s -> s.getEnd().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());

        if (booking.isEmpty()) {
            throw new NullPointerException("Booking doesn't exists");
        }

        User user = userRepository.findById(authorId)
                .orElseThrow(() -> new UserIdNotValidation("Пользователь с id " + authorId + " не найден."));

        Item item = itemRepository.findById(itemId)
                        .orElseThrow(() -> new NotFoundException("Продукт с id " + itemId + " не найден."))

        commentDto.setItemId(itemId);
        commentDto.setAuthorName(user.getName());
        Comment comment = CommentMapper.toComment(commentDto, item, user, LocalDateTime.now());

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    private List<CommentDto> getComments(Long itemId) {
        return commentRepository.getCommentByItemId(itemId).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
