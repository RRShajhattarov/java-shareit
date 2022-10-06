package ru.practicum.shareit.booking;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

private BookingService bookingService;

@PostMapping
public BookingDto toBook(@RequestBody BookingDto bookingDto,
                         @RequestHeader("X-Sharer-User-Id") Integer userId) {
    return bookingService.toBook(bookingDto, userId);
}


}
