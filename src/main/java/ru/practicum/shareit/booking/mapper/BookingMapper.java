package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {


    public static BookingDto toBookingDto(Booking book) {
        BookingDto dto = new BookingDto();
        dto.setId(book.getId());
        dto.setItemId(book.getItem().getId());
        dto.setStart(book.getStart());
        dto.setEnd(book.getEnd());
        return dto;
    }

    public static Booking toBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        return booking;
    }
}
