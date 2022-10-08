package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.enums.BookingStateEnum;
import ru.practicum.shareit.enums.BookingStatus;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private BookingService bookingService;

    @PostMapping
    public BookingDto addBooking(@RequestBody BookingDto bookingDto,
                             @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.addBooking(bookingDto, userId);
    }

//    @PatchMapping("/{bookingId}")
//    public BookingDto changeStatus(@PathVariable("bookingId") Long bookingId,
//                                   @RequestParam("approved") Boolean approved) {
//        return bookingService.changeStatus(bookingId, approved);
//    }
//
//    @GetMapping("/{bookingId}")
//    public BookingDto getBooking(@PathVariable("bookingId") Long bookingId) {
//        return bookingService.getBooking(bookingId);
//    }
//
//    @GetMapping
//    public BookingDto getAllBookings(@RequestParam(value = "state", defaultValue = "ALL", required = false) BookingStateEnum state,
//                                     @RequestHeader(value="X-Sharer-User-Id") Long userId) {
//        return bookingService.getAllBookings(state, userId);
//    }
//
//    @GetMapping("/owner")
//    public BookingDto getOwnerBookings(@RequestParam(value = "state", defaultValue = "ALL", required = false) BookingStateEnum state,
//                                       @RequestHeader(value="X-Sharer-User-Id") Long userId) {
//        return bookingService.getOwnerBookings(state, userId);
//    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
}
