package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingLongDto;
import ru.practicum.shareit.booking.dto.BookingWithDateDto;
import ru.practicum.shareit.booking.model.Booking;

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

    public static BookingLongDto toBookingLongDto(Booking book) {
        BookingLongDto dto = new BookingLongDto();
        dto.setId(book.getId());
        dto.setStatus(book.getStatus());
        dto.setBookerId(book.getBooker().getId());
        dto.setItemId(book.getItem().getId());
        dto.setItemName(book.getItem().getName());
        return dto;
    }

    public static BookingWithDateDto toBookingWithDateDto(Booking book) {
        BookingWithDateDto dto = new BookingWithDateDto();
        dto.setId(book.getId());
        dto.setStart(book.getStart());
        dto.setEnd(book.getEnd());
        dto.setStatus(book.getStatus());
        dto.setBookerId(book.getBooker().getId());
        dto.setItemId(book.getItem().getId());
        dto.setItemName(book.getItem().getName());
        return dto;
    }

}
