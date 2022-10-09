package ru.practicum.shareit.booking.validation;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DateValidator {

    public boolean validateDtoDate(BookingDto bookingDto) {
        if (bookingDto.getEnd().isBefore(LocalDateTime.now())
         || bookingDto.getStart().isBefore(LocalDateTime.now())
         || bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            return false;
        }
        return true;
    }
}
