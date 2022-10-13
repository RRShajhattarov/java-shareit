package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.dto.BookingLongDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemGetDto;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setOwner(item.getOwner());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }

    public static Item toItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setOwner(itemDto.getOwner());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        return item;
    }

    public static ItemGetDto toItemGetDto(Item item, LocalDateTime lastBooking, LocalDateTime nextBooking) {
        ItemGetDto itemGetDto = new ItemGetDto();
        itemGetDto.setId(item.getId());
        itemGetDto.setName(item.getName());
        itemGetDto.setDescription(item.getDescription());
        itemGetDto.setAvailable(item.getAvailable());
        itemGetDto.setStartBooking(lastBooking);
        itemGetDto.setEndBooking(nextBooking);
        return itemGetDto;
    }
}
