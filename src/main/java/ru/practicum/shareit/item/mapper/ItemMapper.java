package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemGetDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner()
        );
    }

    public static Item toItem(ItemDto itemDto, User user ) {
        Item item = new Item(
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                user
        );
        if(itemDto.getId() != null) {
            item.setId(itemDto.getId());
        }
        return item;

    }

    public ItemGetDto itemGetBookingDto(Item item,
                                        Booking lastBooking, Booking nextBooking) {
        return new ItemGetDto(item.getId(), item.getName(),
                item.getDescription(), item.getAvailable(),
                new BookingDto(lastBooking.), nextBooking);
    }

}
