package ru.practicum.shareit.booking.validation;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.time.LocalDate;
import java.util.Date;

@Service
public class DateValidator {

    public boolean validateDtoDate(BookingDto bookingDto) {
        if (bookingDto.getEnd().isBefore(LocalDate.now())
         || bookingDto.getStart().isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }
}
