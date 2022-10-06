package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.storage.BookingRepository;

@Service
@RequiredArgsConstructor
public class BookingService {
    private BookingRepository bookingRepository;
    public BookingDto toBook(BookingDto bookingDto, Integer userId) {
    }
}    
