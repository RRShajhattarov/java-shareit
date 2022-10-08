package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class ItemGetDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private LocalDate startBooking;
    private LocalDate endBooking;
}
